package Sys.vehiculos.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Sys.vehiculos.Dtos.VehiculoRecordDto;
import Sys.vehiculos.Model.VehiculosModel;
import Sys.vehiculos.Repositories.VehiculoRepository;



@Service
public class VehiculoServices {
    
    @Autowired
    private VehiculoRepository vehiculoRepository;
    
    public VehiculosModel createVehiculo(VehiculoRecordDto vehiculoDto, MultipartFile imagem) {
        VehiculosModel vehiculo = new VehiculosModel();
        BeanUtils.copyProperties(vehiculoDto, vehiculo);
        if (!imagem.isEmpty()) {
            try {
                byte[] bytes = imagem.getBytes();
                Path path = Paths.get("./src/main/resources/static/img/" + imagem.getOriginalFilename());
                Files.write(path, bytes);
                vehiculo.setImagem(imagem.getOriginalFilename());
            } catch (IOException e) {
               e.getStackTrace();
                throw new RuntimeException("Error uploading image", e);
            }
        }
        return vehiculoRepository.save(vehiculo);
    }

}
