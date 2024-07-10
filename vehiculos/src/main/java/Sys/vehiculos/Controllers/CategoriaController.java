package Sys.vehiculos.Controllers;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import Sys.vehiculos.Dtos.CategoriaRecordDto;
import Sys.vehiculos.Model.CategoriasModel;
import Sys.vehiculos.Model.VehiculosModel;
import Sys.vehiculos.Repositories.CategoriaRepository;
import jakarta.validation.Valid;

@RestController
public class CategoriaController {
	
	@Autowired
	CategoriaRepository repositorio;
	
	@PostMapping("/categorias")
	public ResponseEntity<CategoriasModel> salvarCategoria(@RequestBody @Valid CategoriaRecordDto categoriaDto){
		
		var categoriasModel = new CategoriasModel();
		BeanUtils.copyProperties(categoriaDto, categoriasModel );
		return
		ResponseEntity.status(HttpStatus.CREATED).body(repositorio.save(categoriasModel));
	}
	

	@GetMapping("/categorias")
	public ResponseEntity<List<CategoriasModel>> listarCategorias(){
		
		return ResponseEntity.status(HttpStatus.OK).body(repositorio.findAll());
		
	}
	
	   @GetMapping("/admin/{categoria}")
	    public ModelAndView listarPorCategoria(@PathVariable("categoria") String categoria) {
	      ModelAndView mv = new ModelAndView("admin/home");
	      CategoriasModel cat = repositorio.findCategoriaByNome(categoria);
	      Set<VehiculosModel> vehiculos = cat.getVehiculos();
	      mv.addObject("vehiculos",vehiculos);    
	   
	      return mv;
	    }
	   
	   
	   
	   
	@GetMapping("/categorias/{id}")
	public ResponseEntity<Object> listarCategoria(@PathVariable(value="id") int id){
		
		Optional<CategoriasModel> categoria = repositorio.findById(id);
		
		if (categoria.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada...");
		}
		return ResponseEntity.status(HttpStatus.OK).body(categoria.get());
	}
	
	@PutMapping("/categorias/{id}")
	public ResponseEntity<Object> editarCategoria(@RequestBody @Valid CategoriaRecordDto categoriaDto ,@PathVariable(value="id") int id ){
		
		Optional<CategoriasModel> categoria=repositorio.findById(id);
		if(categoria.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria nao encontrada...");
			
		}
		var categoriaModel = categoria.get();
		BeanUtils.copyProperties(categoriaDto,categoriaModel );
		return ResponseEntity.status(HttpStatus.OK).body(repositorio.save(categoriaModel));
	}
	
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<Object> deltarCategoria(@PathVariable (value="id") int id){
		
		Optional<CategoriasModel> categoria = repositorio.findById(id);
		if(categoria.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria nao encontrada...");
			
			
		}
		repositorio.delete(categoria.get());
		
		return ResponseEntity.status(HttpStatus.OK).body("Categoria deletada com sucesso!");
		
	}
	
	
	
	

	
	
	
	

}
