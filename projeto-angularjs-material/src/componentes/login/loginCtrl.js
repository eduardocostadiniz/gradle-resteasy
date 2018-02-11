angular.module("meuProjeto").controller('LoginCtrl', function($scope, $location, $mdTheming, $http, $rootScope, mensagens) {

  $rootScope.alertaGlobal = mensagens;

  $scope.usuario = {};
  // $scope.usuario.usuario = "eduardoc";
  // $scope.usuario.senha = "1234";

  $scope.loginMensagem = "Faça login na sua conta para continuar.";

  var inserirTokenUsuario = function(token) {
    if (typeof(Storage) !== "undefined") {
      // console.log("Code for localStorage/sessionStorage.");
      //$rootScope.tokenUsr = token;
      window.localStorage.setItem("tokenUsr", token);
    }else {
      // console.log("Sorry! No Web Storage support..");
    }
  }

  $scope.logarUsuario = function (usuario) {
    $http.post("http://localhost:8080/backend-jwt/api/endpoint/logar", usuario).then(function(response) {
      // successCallback
      // console.log("SUCESSO");
      // console.log(response);
      //agora o servidor retorna um objeto, pegar de acordo com o objeto retornado
      //response.data.Authorization
      inserirTokenUsuario(response.data);
      $location.path("/");

    }, function(response) {
      // errorCallback
      console.log("FALHA");
      console.log(response);
    });
  }

  //obter o tema principal para montar o gradiente linear
  var obterTemaPrincipal = function() {
    var nomeCorSelecionada = $mdTheming.THEMES.eduardo.colors.primary.name;
    var rgbCorTema = $mdTheming.PALETTES[nomeCorSelecionada][500].value.toString();
    rgbCorTema = "rgb(" + rgbCorTema + ")";
    return "linear-gradient(to bottom, " + rgbCorTema + " 0%, " + rgbCorTema + " 50%, #FFFFFF 51%, #FFFFFF 100%)";
  }

  $scope.corTemaSelecionado = obterTemaPrincipal();

});
