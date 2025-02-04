'use strict';

setPageScale(0.9);

function setPageScale(scale) {
  document.body.style.transform = `scale(${scale})`;
  document.body.style.transformOrigin = '0 0'; // Assicura che l'origine dello scale sia l'angolo in alto a sinistra
  document.body.style.width = `${100 / scale}%`;
}
