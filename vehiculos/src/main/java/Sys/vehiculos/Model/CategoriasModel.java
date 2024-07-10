package Sys.vehiculos.Model;



import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="categoria")
public class CategoriasModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "nome")
	private String nome;

	
	@OneToMany(mappedBy= "categoria", cascade= CascadeType.ALL)
	private Set<VehiculosModel> vehiculos;


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}


	public Set<VehiculosModel> getVehiculos() {
		return vehiculos;
	}


	public void setVehiculos(Set<VehiculosModel> vehiculos) {
		this.vehiculos = vehiculos;
	}


	public void setId(int id) {
		this.id = id;
	}
	
}
