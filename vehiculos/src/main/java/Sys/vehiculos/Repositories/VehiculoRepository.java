package Sys.vehiculos.Repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Sys.vehiculos.Dtos.VehiculoRecordDto;
import Sys.vehiculos.Model.VehiculosModel;
import jakarta.validation.Valid;


public interface  VehiculoRepository extends JpaRepository<VehiculosModel, Integer> {
	
	List<VehiculosModel> findVehiculosByAno(int ano);
	List<VehiculosModel> findVehiculosByModeloLike(String modelo);
	void saveAndFlush(@Valid VehiculoRecordDto vehiculosDto);
	

	}
