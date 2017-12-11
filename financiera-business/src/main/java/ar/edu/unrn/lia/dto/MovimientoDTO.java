package ar.edu.unrn.lia.dto;

import java.math.BigDecimal;

public class MovimientoDTO {
    private String anio;
    private String mes;
    private String dia;
    private BigDecimal monto;

    public MovimientoDTO(Integer anio, Integer mes, Integer dia,  BigDecimal monto) {
        this.anio = anio.toString();
        this.mes = mes.toString();
        this.dia = dia.toString();
        this.monto = monto;
    }

    public MovimientoDTO(Integer anio, Integer mes, Integer dia,  Long monto) {
        this.anio = anio.toString();
        this.mes = mes.toString();
        this.dia = dia.toString();
        this.monto = BigDecimal.valueOf(monto);
    }

    public String getAnio() {
        return anio;
    }

    public String getMes() {
        return mes;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public String getDia() {
        return dia;
    }
}
