package Sys.vehiculos.Model;



import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "vehiculo")
public class VehiculosModel implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	public VehiculosModel(int ano, String placa, String cor, String modelo, String marca, String imagem) {
		super();
		this.ano = ano;
		this.placa = placa;
		this.cor = cor;
		this.modelo = modelo;
		this.marca = marca;
		this.imagem = imagem;
		//this.categoria=categoria;
	}
	//int cat = this.getCategoriaId();
	
	public VehiculosModel() {
		
	}
	
	private int ano;
	private String placa;
	private String cor;
	private String modelo;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String marca;
	private String imagem;
	
	@ManyToOne
	@JoinColumn
	private CategoriasModel categoria;

	public CategoriasModel getCategoria() {
		return categoria;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCategoriaNome() {
		return categoria.getNome();
	}
	public int getCategoriaId() {
		return categoria.getId();
	}
		public void setCategoria(CategoriasModel categoria) {
		this.categoria = categoria;
	}
		
	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	
	
	
}
