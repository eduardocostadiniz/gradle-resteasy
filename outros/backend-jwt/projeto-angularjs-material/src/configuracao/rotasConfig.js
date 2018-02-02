angular.module("meuProjeto").config(function($stateProvider, $urlRouterProvider) {

  $urlRouterProvider.otherwise('/login');

  $stateProvider.state("main", {
    url: "/",
    templateUrl: "src/componentes/main.html",
    controller: "MainCtrl"
  })
  .state("login", {
    url: "/login",
    templateUrl: "src/componentes/login/login.html",
    controller: "LoginCtrl",
  })
  .state("logout", {
    url: "/logout",
    controller: "LogoutCtrl",
  })
  .state("main.dashboard", {
    url: "dashboard",
    templateUrl: "src/componentes/dashboard/dashboard.html",
    controller: "DashboardCtrl",
  })
  .state("main.usuario", {
    url: "usuario",
    templateUrl: "src/componentes/usuario/usuario.html",
    controller: "UsuarioCtrl",
  });

});
