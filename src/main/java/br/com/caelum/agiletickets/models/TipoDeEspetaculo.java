package br.com.caelum.agiletickets.models;

import java.math.BigDecimal;

public enum TipoDeEspetaculo {
	
	CINEMA(new BigDecimal("0.10"), 0.05),
	SHOW(new BigDecimal("0.10"),0.05),
	TEATRO(new BigDecimal("1"),0.0), 
	BALLET(new BigDecimal ("0.20"),0.5),
	ORQUESTRA(new BigDecimal ("0.20"),0.5);
	
	private BigDecimal aliquota;
	private Double percentual;
	
	private TipoDeEspetaculo(BigDecimal aliquota, Double percentual) {
		this.aliquota = aliquota;
		this.percentual = percentual;
	}

	public BigDecimal getAliquota() {
		return aliquota;
	}

	public Double getPercentual() {
		return percentual;
	}
	
	
}
