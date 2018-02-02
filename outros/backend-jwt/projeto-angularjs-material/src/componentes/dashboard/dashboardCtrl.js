angular.module("meuProjeto").controller("DashboardCtrl", function($scope, $location, $rootScope, $http) {
  $scope.dashboardMensagem = "Dashboard Controller";

  $scope.itens = [];
  $scope.limiteLinhas = 5;
  $scope.nroPagina = 1;

  // var objRef = {
  //   id: 0,
  //   nome: "",
  //   email: ""
  // };
  //
  // for(i=1; i< 10; i++) {
  //   objRef.id = i;
  //   objRef.nome = "Nome " + i + " nome";
  //   objRef.email = "email" + i + "@email.com";
  //   $scope.itens.push(angular.copy(objRef));
  // }

  $scope.listarDashboard = function() {
    $http.get("http://localhost:8080/backend-jwt/api/endpoint/dashboard").then(function(response) {
      $scope.itens = response.data;
    }, function(response) {
      //error
    });
  }

  $scope.token = window.localStorage.getItem("tokenUsr");

});
