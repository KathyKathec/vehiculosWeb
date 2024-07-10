package Sys.vehiculos.Dtos;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehiculoRecordDto(@NotBlank String placa, @NotBlank String cor, @NotBlank String modelo, int ano, @NotBlank String marca, @NotNull int categoriaId, String imagem) {

}

