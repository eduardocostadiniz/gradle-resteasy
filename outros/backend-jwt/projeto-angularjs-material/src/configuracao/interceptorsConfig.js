angular.module("meuProjeto").config(function($httpProvider) {
  $httpProvider.interceptors.push("HttpInterceptor");
});
