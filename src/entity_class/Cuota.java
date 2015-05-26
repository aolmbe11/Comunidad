/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_class;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Angel
 */
@Entity
@Table(name = "cuota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuota.findAll", query = "SELECT c FROM Cuota c"),
    @NamedQuery(name = "Cuota.findById", query = "SELECT c FROM Cuota c WHERE c.id = :id"),
    @NamedQuery(name = "Cuota.findByAgua", query = "SELECT c FROM Cuota c WHERE c.agua = :agua"),
    @NamedQuery(name = "Cuota.findByLuz", query = "SELECT c FROM Cuota c WHERE c.luz = :luz"),
    @NamedQuery(name = "Cuota.findByBasura", query = "SELECT c FROM Cuota c WHERE c.basura = :basura"),
    @NamedQuery(name = "Cuota.findByZonasComunes", query = "SELECT c FROM Cuota c WHERE c.zonasComunes = :zonasComunes"),
    @NamedQuery(name = "Cuota.findByOtros", query = "SELECT c FROM Cuota c WHERE c.otros = :otros"),
    @NamedQuery(name = "Cuota.findByTotal", query = "SELECT c FROM Cuota c WHERE c.total = :total"),
    @NamedQuery(name = "Cuota.findByFecha", query = "SELECT c FROM Cuota c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Cuota.findByPagado", query = "SELECT c FROM Cuota c WHERE c.pagado = :pagado")})
public class Cuota implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "agua")
    private BigDecimal agua;
    @Basic(optional = false)
    @Column(name = "luz")
    private BigDecimal luz;
    @Basic(optional = false)
    @Column(name = "basura")
    private BigDecimal basura;
    @Basic(optional = false)
    @Column(name = "zonas_comunes")
    private BigDecimal zonasComunes;
    @Basic(optional = false)
    @Column(name = "otros")
    private BigDecimal otros;
    @Basic(optional = false)
    @Column(name = "total")
    private BigDecimal total;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "pagado")
    private boolean pagado;
    @JoinColumn(name = "vivienda", referencedColumnName = "id")
    @ManyToOne
    private Vivienda vivienda;

    public Cuota() {
    }

    public Cuota(Integer id) {
        this.id = id;
    }

    public Cuota(Integer id, BigDecimal agua, BigDecimal luz, BigDecimal basura, BigDecimal zonasComunes, BigDecimal otros, BigDecimal total, Date fecha, boolean pagado) {
        this.id = id;
        this.agua = agua;
        this.luz = luz;
        this.basura = basura;
        this.zonasComunes = zonasComunes;
        this.otros = otros;
        this.total = total;
        this.fecha = fecha;
        this.pagado = pagado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAgua() {
        return agua;
    }

    public void setAgua(BigDecimal agua) {
        this.agua = agua;
    }

    public BigDecimal getLuz() {
        return luz;
    }

    public void setLuz(BigDecimal luz) {
        this.luz = luz;
    }

    public BigDecimal getBasura() {
        return basura;
    }

    public void setBasura(BigDecimal basura) {
        this.basura = basura;
    }

    public BigDecimal getZonasComunes() {
        return zonasComunes;
    }

    public void setZonasComunes(BigDecimal zonasComunes) {
        this.zonasComunes = zonasComunes;
    }

    public BigDecimal getOtros() {
        return otros;
    }

    public void setOtros(BigDecimal otros) {
        this.otros = otros;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean getPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public Vivienda getVivienda() {
        return vivienda;
    }

    public void setVivienda(Vivienda vivienda) {
        this.vivienda = vivienda;
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
        if (!(object instanceof Cuota)) {
            return false;
        }
        Cuota other = (Cuota) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity_class.Cuota[ id=" + id + " ]";
    }
    
}
