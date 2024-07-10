package Sys.vehiculos.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Sys.vehiculos.Model.CategoriasModel;


@Repository
public interface CategoriaRepository extends JpaRepository<CategoriasModel, Integer> {
 
	CategoriasModel findCategoriaByNome(String nome);
	
}
