package com.jortiz.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "clientes")
@Entity
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@NotEmpty debe estar acompañado del @Size y se le puede añadir el mensaje de salida en el front  @NotEmpty(message = "no puede estar ")*/
    @NotEmpty(message = "es obligatorio!")
    @Size(min = 4, max = 20)
    /*los campos por defecto son nulos*/
    @Column(nullable = false)
    private  String nombre;

    @NotEmpty
    private  String apellido;


    @Email
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String email;


    @Column(name = "create_at")
    /*transforma la fecha de java a la fecha de mysql*/
    @Temporal(TemporalType.DATE)
    private Date createAt;

    private String foto;

    @NotNull(message = "la region no puede estar vacia")
    @ManyToOne(fetch = FetchType.LAZY)
    /*"region_id" nombre de la llave forenea en la tabla region del padre  clientes*/
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;


    @JsonIgnoreProperties(value = {"cliente","hibernateLazyInitializer", "handler"}, allowSetters = true)
    /*atributo de la tabla hijo "cliente" de la clase factura*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Factura> facturas;

    /*crear constructor en el padre*/
    public Cliente() {
        this.facturas = new ArrayList<>();
    }

    /*FOTO*/
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

//    /*crea la fecha del cliente automaticamente cuando se crea un cliente*/
//    @PrePersist
//    public void prePersist(){
//        createAt = new Date();
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public static final long serialVersionUID = 1L;
}
