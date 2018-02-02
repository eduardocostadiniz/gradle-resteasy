angular.module("meuProjeto", ["ngMaterial", "ngMaterialSidemenu", "ui.router", "md.data.table"]).controller("MainCtrl", function($scope, $location, $mdSidenav) {

  const ID_MENU_ESQUERDO = "menuEsquerdo";

  $scope.app = "Projeto AngularJS com Angular-Material";

  $scope.abrirFecharMenu = function() {
    $mdSidenav(ID_MENU_ESQUERDO).toggle();
  }

  $location.path("dashboard");

});
