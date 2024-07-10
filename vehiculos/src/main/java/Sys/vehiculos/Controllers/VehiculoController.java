package Sys.vehiculos.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import Sys.vehiculos.Dtos.VehiculoRecordDto;
import Sys.vehiculos.Model.CategoriasModel;
import Sys.vehiculos.Model.VehiculosModel;
import Sys.vehiculos.Repositories.CategoriaRepository;
import Sys.vehiculos.Repositories.VehiculoRepository;
import jakarta.validation.Valid;

@Controller
public class VehiculoController {

	@Autowired
	VehiculoRepository vehiculosRepository;
	@Autowired
	CategoriaRepository categoriasRepository;

	@PostMapping("/vehiculos")
	public ResponseEntity<Object> salvarVehiculo(@RequestBody @Valid VehiculoRecordDto vehiculoDto) {
	    Optional<CategoriasModel> categoria = categoriasRepository.findById(vehiculoDto.categoriaId());

	    if (categoria.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada... Informe uma categoria existente");
	    }

	    var vehiculo = new VehiculosModel();
	    BeanUtils.copyProperties(vehiculoDto, vehiculo);
	    vehiculo.setCategoria(categoria.get());

	    return ResponseEntity.status(HttpStatus.CREATED).body(vehiculosRepository.save(vehiculo));
	}


	
	
	@GetMapping("/vehiculos")
	public ResponseEntity<List<VehiculosModel>> listarVehiculos(){
		return ResponseEntity.status(HttpStatus.OK).body(vehiculosRepository.findAll());
	}
	
	@GetMapping("/vehiculos/{id}")
	public ResponseEntity<Object> listaUmVehiculo(@PathVariable(value="id") int id){
		
		Optional <VehiculosModel> vehiculo = vehiculosRepository.findById(id);
		if(vehiculo.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehiculo nao encontrado...");
		}
		return ResponseEntity.status(HttpStatus.OK).body(vehiculo.get());
	}
	
/*
 * //agregar buscar por ano
	
	//agregar buscar por modelo
	@GetMapping("/vehiculos")
	public ResponseEntity<List<VehiculosModel>> listarVehiculosModelo(@RequestParam("src") String src){
	
	return ResponseEntity.status(HttpStatus.OK).body(vehiculosRepository.findVehiculosByModeloLike("%"+src+"%")) ;
	
	
	}
 */
	
	
	@PutMapping("vehiculos/{id}")
	public ResponseEntity<Object> editarVehiculo(@RequestBody @Valid VehiculoRecordDto vehiculoDto, @PathVariable(value="id") int id){
		
		Optional<VehiculosModel> vehiculo = vehiculosRepository.findById(id);
		
		if(vehiculo.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehiculo nao encontrado...");
			
		}
		  Optional<CategoriasModel> categoria = categoriasRepository.findById(vehiculoDto.categoriaId());
		    if (categoria.isEmpty()) {
		      //  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada... Informe uma categoria existente");
		     
		        BeanUtils.copyProperties(vehiculoDto, vehiculo.get());
				return ResponseEntity.status(HttpStatus.OK).body(vehiculosRepository.save(vehiculo.get()));
				
		    }
	vehiculo.get().setCategoria(categoria.get());
	BeanUtils.copyProperties(vehiculoDto, vehiculo.get());
		return ResponseEntity.status(HttpStatus.OK).body(vehiculosRepository.save(vehiculo.get()));
		
	}
	
	@DeleteMapping("/vehiculos/{id}")
	public ResponseEntity<Object> deletarVehiculo(@PathVariable(value="id") int id){
		
		Optional<VehiculosModel> vehiculos = vehiculosRepository.findById(id);
		if(vehiculos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehiculo nao encontrado...");
			
		}
		vehiculosRepository.delete(vehiculos.get());
		return ResponseEntity.status(HttpStatus.OK).body("Vehiculo deletado com sucesso!");
	}
	
	
	
	
}
