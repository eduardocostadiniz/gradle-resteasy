angular.module("meuProjeto").controller("LogoutCtrl", function($scope, $location, $rootScope) {
  console.log("Fazendo logout...");

  window.localStorage.removeItem("tokenUsr");

  // var token = window.localStorage.getItem("token_usr");
  // console.log("Token Logout");
  // console.log(token);
  // window.localStorage.removeItem("token_usr");
  // token = window.localStorage.getItem("token_usr");
  // console.log("Token Excluido");
  // console.log(token);

  $location.path("login");

});
