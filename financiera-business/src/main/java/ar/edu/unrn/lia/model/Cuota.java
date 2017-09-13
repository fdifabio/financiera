package ar.edu.unrn.lia.model;

import java.math.BigDecimal;

/**
 * Created by Lucas on 05/09/2017.
 */
public class Cuota {
    private BigDecimal cuotaCapital;
    private BigDecimal cuotaInteres;
    private BigDecimal saldo;

    public Cuota(BigDecimal cuotaCapital, BigDecimal cuotaInteres, BigDecimal saldo) {
        this.cuotaCapital = cuotaCapital;
        this.cuotaInteres = cuotaInteres;
        this.saldo = saldo;
    }

    public BigDecimal getCuotaCapital() {
        return cuotaCapital;
    }

    public void setCuotaCapital(BigDecimal cuotaCapital) {
        this.cuotaCapital = cuotaCapital;
    }

    public BigDecimal getCuotaInteres() {
        return cuotaInteres;
    }

    public void setCuotaInteres(BigDecimal cuotaInteres) {
        this.cuotaInteres = cuotaInteres;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
