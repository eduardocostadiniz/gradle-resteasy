angular.module('meuProjeto').config(function($mdThemingProvider) {

  const NOME_NOVO_TEMA = "eduardo";

  //constantes de cores do temaConfig
  const VERMELHO = 'red';
  const ROSA = 'pink';
  const ROXO = 'purple';
  const ROXO_ESCURO = 'deep-purple';
  const INDIGO = 'indigo';
  const AZUL = 'blue';
  const AZUL_CLARO = 'light-blue';
  const CIANO = 'cyan';
  const TEAL = 'teal';
  const VERDE = 'green';
  const VERDE_CLARO = 'light-green';
  const LIMAO = 'lime';
  const AMARELO = 'yellow';
  const AMBER = 'amber';
  const LARANJA = 'orange';
  const LARANJA_ESCURO = 'deep-orange';
  const MARROM = 'brown';
  const CINZA = 'grey';
  const AZUL_ACINZENTADO = 'blue-grey';

  $mdThemingProvider.alwaysWatchTheme(true);

  $mdThemingProvider.theme(NOME_NOVO_TEMA).primaryPalette(TEAL);
  $mdThemingProvider.theme(NOME_NOVO_TEMA).accentPalette(LARANJA);
  $mdThemingProvider.theme(NOME_NOVO_TEMA).warnPalette(VERMELHO);
  // $mdThemingProvider.theme(NOME_NOVO_TEMA).backgroundPalette(AMBER);
  // $mdThemingProvider.theme(NOME_NOVO_TEMA).dark();

  $mdThemingProvider.setDefaultTheme(NOME_NOVO_TEMA);

});
