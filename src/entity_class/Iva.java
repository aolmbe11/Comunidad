/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_class;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Angel
 */
@Entity
@Table(name = "iva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Iva.findAll", query = "SELECT i FROM Iva i"),
    @NamedQuery(name = "Iva.findById", query = "SELECT i FROM Iva i WHERE i.id = :id"),
    @NamedQuery(name = "Iva.findByIvaAgua", query = "SELECT i FROM Iva i WHERE i.ivaAgua = :ivaAgua"),
    @NamedQuery(name = "Iva.findByIvaLuz", query = "SELECT i FROM Iva i WHERE i.ivaLuz = :ivaLuz"),
    @NamedQuery(name = "Iva.findByIvaBasura", query = "SELECT i FROM Iva i WHERE i.ivaBasura = :ivaBasura"),
    @NamedQuery(name = "Iva.findByIvaZonasComunes", query = "SELECT i FROM Iva i WHERE i.ivaZonasComunes = :ivaZonasComunes"),
    @NamedQuery(name = "Iva.findByIvaOtros", query = "SELECT i FROM Iva i WHERE i.ivaOtros = :ivaOtros"),
    @NamedQuery(name = "Iva.findByIvaTotal", query = "SELECT i FROM Iva i WHERE i.ivaTotal = :ivaTotal")})
public class Iva implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "iva_agua")
    private BigDecimal ivaAgua;
    @Basic(optional = false)
    @Column(name = "iva_luz")
    private BigDecimal ivaLuz;
    @Basic(optional = false)
    @Column(name = "iva_basura")
    private BigDecimal ivaBasura;
    @Basic(optional = false)
    @Column(name = "iva_zonas_comunes")
    private BigDecimal ivaZonasComunes;
    @Basic(optional = false)
    @Column(name = "iva_otros")
    private BigDecimal ivaOtros;
    @Basic(optional = false)
    @Column(name = "iva_total")
    private BigDecimal ivaTotal;

    public Iva() {
    }

    public Iva(Integer id) {
        this.id = id;
    }

    public Iva(Integer id, BigDecimal ivaAgua, BigDecimal ivaLuz, BigDecimal ivaBasura, BigDecimal ivaZonasComunes, BigDecimal ivaOtros, BigDecimal ivaTotal) {
        this.id = id;
        this.ivaAgua = ivaAgua;
        this.ivaLuz = ivaLuz;
        this.ivaBasura = ivaBasura;
        this.ivaZonasComunes = ivaZonasComunes;
        this.ivaOtros = ivaOtros;
        this.ivaTotal = ivaTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getIvaAgua() {
        return ivaAgua;
    }

    public void setIvaAgua(BigDecimal ivaAgua) {
        this.ivaAgua = ivaAgua;
    }

    public BigDecimal getIvaLuz() {
        return ivaLuz;
    }

    public void setIvaLuz(BigDecimal ivaLuz) {
        this.ivaLuz = ivaLuz;
    }

    public BigDecimal getIvaBasura() {
        return ivaBasura;
    }

    public void setIvaBasura(BigDecimal ivaBasura) {
        this.ivaBasura = ivaBasura;
    }

    public BigDecimal getIvaZonasComunes() {
        return ivaZonasComunes;
    }

    public void setIvaZonasComunes(BigDecimal ivaZonasComunes) {
        this.ivaZonasComunes = ivaZonasComunes;
    }

    public BigDecimal getIvaOtros() {
        return ivaOtros;
    }

    public void setIvaOtros(BigDecimal ivaOtros) {
        this.ivaOtros = ivaOtros;
    }

    public BigDecimal getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(BigDecimal ivaTotal) {
        this.ivaTotal = ivaTotal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iva)) {
            return false;
        }
        Iva other = (Iva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity_class.Iva[ id=" + id + " ]";
    }
    
}
