import java.util.Random;

/**
 * Created by abeken on 2016/12/22.
 */
public class ScaledInverseChiSquaredDistribution {
	private final Random fRandom = new Random();

	public static void main(String[] args) {
		ScaledInverseChiSquaredDistribution sicsd = new ScaledInverseChiSquaredDistribution();
		for (int i = 0; i < 1000; ++i) {
			System.out.println(sicsd.rand_inverse_chi(1.0, 1.0));
		}
	}

	private double rand_inverse_chi(double df, double s2) {
		double chisq = rand_chi((int) df);
		double inv_chisq = (df * s2) / chisq;
		return inv_chisq;
	}

	private double rand_chi(int k) {
		return rand_gamma(k / 2.0, 2.0);
	}

	private double rand_gammma(double alpha) {
		double n;
		if (alpha <= 0.4) {
			n = 1.0 / alpha;
		} else if (alpha <= 4.0) {
			n = 1.0 / alpha + (alpha - 0.4) / (3.6 * alpha);
		} else {
			n = 1.0 / Math.sqrt(alpha);
		}
		double b1 = alpha - 1.0 / n;
		double b2 = alpha + 1.0 / n;
		double c1;
		if (alpha <= 0.4) {
			c1 = 0.0;
		} else {
			c1 = b1 * (Math.log(b1) - 1.0) / 2.0;
		}
		double c2 = b2 * (Math.log(b2) - 1.0) / 2.0;
		double y;
		double x;
		do {
			double w1, w2;
			do {
				double v1 = fRandom.nextDouble();
				double v2 = fRandom.nextDouble();
				w1 = c1 + Math.log(v1);
				w2 = c2 + Math.log(v2);
				y = n * (b1 * w2 - b2 * w1);
			} while (y < 0);
			x = n * (w2 - w1);
		} while (Math.log(y) < x);
		return Math.exp(x);
	}

	private double rand_gamma(double alpha, double beta) {
		return beta * rand_gammma(alpha);
	}
}
