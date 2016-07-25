'use strict';

angular.module('ui.bootstrap.validation')
.value('ErrorMessages', {
	'email': 'Email inválido',
	'max': 'Valor máximo: ',
	'maxlength': 'Tamanho máximo: ',
	'min': 'minimum size ',
	'minlength': 'minimum size',
	'required': 'This is mandatory field',
	'unique': '	This field does not accept repeated values'
});