package ar.edu.unrn.lia.dto;

import java.math.BigDecimal;

public class CuotaDTO {
    private String anio;
    private String mes;
    private BigDecimal monto;

    public CuotaDTO(Integer anio, Integer mes, BigDecimal monto) {
        this.anio = anio.toString();
        this.mes = mes.toString();
        this.monto = monto;
    }

    public CuotaDTO(Integer anio, Integer mes, Long monto) {
        this.anio = anio.toString();
        this.mes = mes.toString();
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
}
