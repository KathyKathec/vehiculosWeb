package Sys.vehiculos.Dtos;



import jakarta.validation.constraints.NotBlank;

public record CategoriaRecordDto(@NotBlank String nome) {

}
