package maomo.vedio.vediolist;

import maomo.vedio.vediobean.VedioModel;

public class VedioInfo {

	public VedioModel vedioUrl;
	public VedioInfo next;

	public VedioInfo(VedioModel vedioUrl) {
		this.vedioUrl = vedioUrl;
	}

	public VedioModel getVedioUrl() {
		return this.vedioUrl;
	}

}
