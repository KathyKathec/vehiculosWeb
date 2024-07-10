package Sys.vehiculos.Controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Sys.vehiculos.Dtos.VehiculoRecordDto;
import Sys.vehiculos.Model.CategoriasModel;
import Sys.vehiculos.Model.VehiculosModel;
import Sys.vehiculos.Repositories.CategoriaRepository;
import Sys.vehiculos.Repositories.VehiculoRepository;
import Sys.vehiculos.Services.VehiculoServices;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/vehiculos")
public class VehiculosController2 {
    
    @Autowired
    private VehiculoRepository vehiculoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private VehiculoServices vehiculoService;
    
    @GetMapping("/admin") //pagina principal, muestra todos
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("admin/home");
        List<CategoriasModel> categorias = categoriaRepository.findAll();
        List<VehiculosModel> vehiculos = vehiculoRepository.findAll();
        mv.addObject("categorias", categorias);
        mv.addObject("vehiculos", vehiculos);
        
        return mv;
    }
    @GetMapping("/admin/categoria/{categoria}")
    public ModelAndView listarPorCategoria(@PathVariable("categoria") String categoria) {
      ModelAndView mv = new ModelAndView("admin/home");
      CategoriasModel cat = categoriaRepository.findCategoriaByNome(categoria);
      Set<VehiculosModel> vehiculos = cat.getVehiculos();
      mv.addObject("vehiculos",vehiculos);    
   
      return mv;
    }
    
  // PROCURAR
    @GetMapping("/admin/ano/{ano}")
    public ModelAndView procuraPorAno(@PathVariable("ano") int ano) {
      ModelAndView mv = new ModelAndView("admin/home");
     List<VehiculosModel> vehiculos= vehiculoRepository.findVehiculosByAno(ano);
    		 
      mv.addObject("vehiculos",vehiculos);    
   
      return mv;
    }
    @GetMapping("/admin/modelo/{modelo}")
    public ModelAndView procuraPorModelo(@PathVariable("modelo") String modelo) {
      ModelAndView mv = new ModelAndView("admin/home");
     List<VehiculosModel> vehiculos= vehiculoRepository.findVehiculosByModeloLike("%" + modelo + "%");
    		 
      mv.addObject("vehiculos",vehiculos);    
   
      return mv;
    }
   // ate aqui tudo certo
    //metodo cadastrar
    @GetMapping("/admin/cadastro") 
    public ModelAndView formulario() {
        ModelAndView mv = new ModelAndView("admin/cadastro");
        List<CategoriasModel> categorias = categoriaRepository.findAll();
        mv.addObject("categorias", categorias);
        //mv.addObject("vehiculoRecordDto", new VehiculoRecordDto("", "", "", 0, "", 0, ""));
        return mv;
    }
    //metodo salvar
  /*  @GetMapping("/admin/salvar") 
    public ModelAndView salvar(@Valid VehiculoRecordDto vehiculosDto,
    		BindingResult result) {
    	if(result.hasErrors()) {
    		return cadastrar(vehiculosDto);
    	}
    	vehiculoRepository.saveAndFlush(vehiculosDto);
    	
      
        return cadastrar(new VehiculoRecordDto(null, null, null, 0, null, 0, null));
    }
     @PostMapping("/admin/salvar") 
    public ModelAndView salvar(@Valid VehiculoRecordDto vehiculosDto,
                                BindingResult result) {
        if(result.hasErrors()) {
            return cadastrar(vehiculosDto);
        }
        vehiculoRepository.saveAndFlush(vehiculosDto);

        // Redirect to the vehicle list page after saving
        return new ModelAndView("redirect:/vehiculos/admin");
    }
    
    */
    @PostMapping("/admin/cadastro")
	public String salvar
	(@ModelAttribute VehiculoRecordDto vehiculosDto,
			BindingResult result, @RequestParam("file") MultipartFile imagem, 
			RedirectAttributes msg){
    	
	if(result.hasErrors()) {
		msg.addFlashAttribute("erroinserir", "Erro ao realizar o cadastro.");
		return "redirect:/vehiculos/admin/cadastro";
	}
	var vehiculosModel = new VehiculosModel();
	BeanUtils.copyProperties(vehiculosDto, vehiculosModel);
	try {
		if(!imagem.isEmpty()) {
			byte[] bytes = imagem.getBytes();
			Path caminho = Paths.get
					("./src/main/resources/static/img/"+imagem.getOriginalFilename());
			Files.write(caminho, bytes);
			vehiculosModel.setImagem(imagem.getOriginalFilename());
		}
	}catch(IOException e) {
		System.out.println("Erro na imagem");
	}
	
	vehiculoRepository.save(vehiculosModel);
	msg.addFlashAttribute("inserirok", "Inserido com sucesso!");
	return "redirect:/vehiculos/admin/home";
}
    
   
    
    @PostMapping("/admin/e")
    public String createVehiculo(@ModelAttribute @Valid VehiculoRecordDto vehiculoDto, 
                                BindingResult result, 
                                @RequestParam("file") MultipartFile imagem, 
                                RedirectAttributes msg) {
        if (result.hasErrors()) {
            msg.addFlashAttribute("erroinserir", "Erro ao realizar o cadastro.");
            return "redirect:/vehiculos/admin/cadastro";
        }
        VehiculosModel vehiculo = vehiculoService.createVehiculo(vehiculoDto, imagem);
        msg.addFlashAttribute("inserirok", "Inserido com sucesso!");
        return "redirect:/vehiculos/admin/home";
    }
    
    
    @GetMapping("/admin/{id}")
    public ModelAndView listarVehiculos(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView("admin/vehiculos/list");
        Optional<VehiculosModel> vehiculos = vehiculoRepository.findById(id);
        VehiculosModel vehiculo = vehiculos.get() ;
        System.out.println(vehiculos.get().getCor());
        mv.addObject("vehiculo", vehiculo);
        return mv;
    }
    /*
    @GetMapping("/vehiculos")
    public String listarVehiculos(Model model, @RequestParam(name = "src", required = false) String src) {
        List<Vehiculo> vehiculos;
        if (src == null) {
            vehiculos = vehiculoService.listarVehiculos();
        } else {
            vehiculos = vehiculoService.buscarVehiculos(src);
        }
        model.addAttribute("vehiculos", vehiculos);
        return "vehiculos/list";
    }
     * 
     */
    @PostMapping("/list")
    public ModelAndView searchVehiculos(@RequestParam("src") String src) {
        ModelAndView mv = new ModelAndView("vehiculos/list");
        List<VehiculosModel> vehiculos = vehiculoRepository.findVehiculosByModeloLike("%" + src + "%");
        mv.addObject("vehiculos", vehiculos);
        return mv;
    }
    
    @GetMapping("/imagem/{imagem}")
    @ResponseBody
    public byte[] mostraImagem(@PathVariable("imagem") String imagem) throws IOException {
        File file = new File("./src/main/resources/static/img/" + imagem);
        return Files.readAllBytes(file.toPath());
    }
    @PostMapping("/delete/{id}")
    public @ResponseBody Map<String, Object> deletarVehiculoId(@PathVariable("id") int id) {
      vehiculoRepository.deleteById(id);
      Map<String, Object> result = new HashMap<>();
      result.put("success", true);
      return result;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletarVehiculo(@PathVariable("id") int id) {
        try {
            vehiculoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
