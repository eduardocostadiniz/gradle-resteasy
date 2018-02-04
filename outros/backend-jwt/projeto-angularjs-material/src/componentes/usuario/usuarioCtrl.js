angular.module("meuProjeto").controller("UsuarioCtrl", function($scope, $http) {
  $scope.usuarioMensagem = "Usuário Controller";

  $scope.guardarTokenUsuarioLogado = function() {
    var dados = $http.get("http://localhost:8080/backend-jwt/api/endpoint/testar");
  }

  $scope.destruirTokenUsuarioLogado = function() {

  }


});
