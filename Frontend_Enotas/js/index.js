//Inicia página web mostrando notas cadastradas no banco
mostrarNotas();

//Binder para alterar cor de fundo das notas de acordo com o id das mesmas 
rivets.binders.color = function(el, value) {

  if(value %2 == 0){

     el.style.backgroundColor = "white";
  }
  else{
    el.style.backgroundColor = "white";

  }

}

//Formatador do rivets para formatar data no formato especificado
//rivets.formatters.['date'] = function(value){ //forma alternativa
rivets.formatters.date = function(value){
  
  return moment(value).format('DD/MM/YYYY')
 
}

//variável que recebe o resultado do "bind" da rivets
var rivetsGetNotas = undefined;

//lista de notas cadastradas
var listaTodasNotas = [];
var listaTodasNotasCopia = [];

//Obter notas cadastradas no banco
function mostrarNotas(){
  $(document).ready(function(){
    $.ajax({
        async: true,
        url: 'http://localhost:8080/enotas/consultar',
        type: 'GET',
        crossDomain: true,
        contentType: 'application/json',
        dataType: 'json',
        withCredentials: true,
        processData: false,
    })    
    .done(function(nota){

      //O "bind" só pode ser realizado uma única vez

      if(rivetsGetNotas != undefined){
        //se houver alterações atualiza as notas obtidas sem realizar novo "bind"
        rivetsGetNotas.update({nota});

      }
      else{
        //rivets obtém todas as notas cadastradas através do rv-each
        rivetsGetNotas = rivets.bind($("#notas"), {nota});

      }

      listaTodasNotas = nota

    })
    .fail(function(err, msg){
      alert(msg)
    })
  })
}


var formDataPost = new FormData();

//Cadastrar novas notas no banco
  $("#form").submit(function(){
    event.preventDefault();

    formDataPost = JSON.stringify($("#form").serializeObject());

    //Quando há clique no botão é chamado o método "post" que insere no banco de dados
    $.ajax({
  
      async: true,
      url: 'http://localhost:8080/enotas/publicar',
      type: 'POST',
      crossDomain: true,
      contentType: 'application/json',
      dataType: 'json',
      withCredentials: true,
      processData: false,
    
      data: formDataPost,
    
      success: function(result) { //Em caso de sucesso 

        $('#myModal').modal('hide'); //Esconde modal

        //uso do Sweetalert para informar o usuário da ação realizada
        swal("Nota cadastrada!", "Ela foi inserida no id: " + result.id, "success");

        console.log(result); //dados retornados pelo requisição "post"

        listaTodasNotas.push(result); //insere nova nota cadastrada na lista de notas

        rivetsGetNotas.update({listaTodasNotas}); //atualiza a página somente para a nota cadastrada

        //location.reload(); //atuliza a página toda após inserção de uma nova nota
        
      },
      erros: function(err, msg){ //Em caso de falha
        //alert(msg)
        swal("Oops...", "Erro ao tentar cadastrar nota \n" + msg, "error");
      }

    });

  });


  //Método para deletar nota registrada no banco 
  function deletarNota(nota){

    var obterId = parseInt(nota.getAttribute("data"));

    $.ajax({
  
      async: true,
      url: 'http://localhost:8080/enotas/deletar/' + obterId,
      type: 'DELETE',
      crossDomain: true,
      contentType: 'application/json',
      dataType: 'json',
      withCredentials: true,
      processData: false,
    
      success: function(result) { //Em caso de sucesso 

        //atualiza a lista de notas, filtrando (eliminando) conforme a condição específicada 
        listaTodasNotas = listaTodasNotas.filter((objeto) => objeto.id != obterId);

        rivetsGetNotas.update({listaTodasNotas}); 

        location.reload(); //atuliza a página após exclusão de uma nota

        //swal("Nota excluida!", "id: " + obterId, "success");

        //console.log(listaTodasNotas)

      },
      erros: function(err, msg){ //Em caso de falha
        swal("Oops...", "Erro ao tentar deletar a nota  \n" + msg, "error");

      }

    });
  }


  //variável para viabilizar a serialização do objeto JSON
  var formDataPut = new FormData();

  //variável receberá "bind" da nota a ser editada
  var rivetsGetNota = undefined;

  //Método para colocar dados da nota no modal de edição
  function editarNotaButton(nota){

    var id = parseInt(nota.getAttribute("data"));
    
    var notaSelecionada = listaTodasNotas.find(objeto => objeto.id == id);

    //Modo eficiente o bind da rivets captura todos os atributos do objeto, dados do form
    if(rivetsGetNota != undefined){
      rivetsGetNota.update({notaSelecionada});

    }
    else{
      rivetsGetNota = rivets.bind($("#form_edit"), {notaSelecionada});

    }

    /*
    //Passando valor para os "inputs" do form de edição utilizando jquery
    //{Modo ineficiente, pois poderia haver muitos campos, isto é atributos do objeto}
    $("#id_edit").val(notaSelecionada.id);
    $("#titulo_edit").val(notaSelecionada.titulo);
    $("#texto_edit").val(notaSelecionada.texto);
    $("#data_edit").val(notaSelecionada.data);
    */

      $("#form_edit").submit(function(){
        event.preventDefault();
    
        formDataPut = JSON.stringify($("#form_edit").serializeObject());

        //Quando há clique no botão é chamado o método "put" que altera o registro no banco de dados
        $.ajax({
      
          async: true,
          url: 'http://localhost:8080/enotas/editar/' + id,
          type: 'PUT',
          crossDomain: true,
          contentType: 'application/json',
          dataType: 'json',
          withCredentials: true,
          processData: false,
        
          data: formDataPut,
        
          success: function(result) { //Em caso de sucesso 

            //console.log(result); //dados retornados pelo requisição "put"
    
            $('#myModal2').modal('hide'); //Esconde modal
    
            swal("Nota editada!", "id: " + result.id, "success");

            listaTodasNotas = listaTodasNotas.filter((objeto) => objeto.id !== id);

            listaTodasNotas.push(result);

            rivetsGetNotas.update(listaTodasNotas);

          },
          erros: function(err, msg){ //Em caso de falha
            swal("Oops...", "Erro ao editar nota \n" + msg, "error");
          }
          
        });
    
      });
  

  }