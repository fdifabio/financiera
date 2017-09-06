package ar.edu.unrn.lia.model;

/**
 * Created by Lucas on 05/09/2017.
 */
public class Cuota {
    private Float cuotaCapital;
    private Float cuotaInteres;
    private Float saldo;

    public Cuota(Float cuotaCapital, Float cuotaInteres, Float saldo) {
        this.cuotaCapital = cuotaCapital;
        this.cuotaInteres = cuotaInteres;
        this.saldo = saldo;
    }

    public Float getCuotaCapital() {
        return cuotaCapital;
    }

    public void setCuotaCapital(Float cuotaCapital) {
        this.cuotaCapital = cuotaCapital;
    }

    public Float getCuotaInteres() {
        return cuotaInteres;
    }

    public void setCuotaInteres(Float cuotaInteres) {
        this.cuotaInteres = cuotaInteres;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }
}
