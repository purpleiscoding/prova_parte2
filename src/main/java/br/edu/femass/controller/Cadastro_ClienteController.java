package br.edu.femass.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import br.edu.femass.dao.LojaDao;
import br.edu.femass.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Cadastro_ClienteController implements Initializable {
    private final LojaDao LojaDao = new LojaDao();

    @FXML
    private ListView<Cliente> LstClientes;

    @FXML
    private Button BtnIncluir;

    @FXML
    private Button BtnExcluir;

    @FXML
    private Button BtnGravar;

    @FXML
    private Button BtnCancelar;

    @FXML
    private TextField TxtNome;

    @FXML
    private TextField TxtSobrenome;


    private void limparTela() {
        TxtSobrenome.setText("");
        TxtNome.setText("");
    }
    private void habilitarInterface(Boolean incluir) {
        TxtNome.setDisable(!incluir);
        TxtSobrenome.setDisable(!incluir);
        BtnGravar.setDisable(!incluir);
        BtnCancelar.setDisable(!incluir);
        BtnExcluir.setDisable(incluir);
        BtnIncluir.setDisable(incluir);
        LstClientes.setDisable(incluir);
    }

    private void exibirCliente() {
        Cliente cliente = LstClientes.getSelectionModel().getSelectedItem();
        if (cliente==null) return;
        TxtNome.setText(cliente.getNome());
        TxtSobrenome.setText(cliente.getSobreNome());

    }

    @FXML
    private void LstClientes_MouseClicked(MouseEvent evento) {
        exibirCliente();
    }

    @FXML
    private void LstClientes_KeyPressed(KeyEvent evento) {
        exibirCliente();
    }

    @FXML
    private void BtnIncluir_Action(ActionEvent evento) {
        atualizarLista();
        habilitarInterface(true);
        limparTela();
        TxtNome.requestFocus();
    }

    @FXML
    private void BtnExcluir_Action(ActionEvent evento) {
        Cliente cliente = LstClientes.getSelectionModel().getSelectedItem();

        if (cliente==null) return;

        try {
            LojaDao.excluir(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }

        atualizarLista();

    }
    @FXML
    private void BtnGravar_Action(ActionEvent evento) {
        Cliente cliente = new Cliente();
        cliente.setSobreNome(TxtSobrenome.getText());
        cliente.setNome(TxtNome.getText());
        if (TxtSobrenome.getText() == "" || TxtNome.getText() == ""){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.show();
        }
        else {
            try {
                LojaDao.gravar(cliente);
            } catch (Exception e) {
                e.printStackTrace();
            }
            atualizarLista();
            habilitarInterface(false);
        }
    }
    @FXML
    private void BtnCancelar_Action(ActionEvent evento) {
        habilitarInterface(false);
    }


    private void atualizarLista() {
        List<Cliente> Clientes;
        try {
            Clientes = LojaDao.listar();
        } catch (Exception e) {
            Clientes = new ArrayList<>();
        }
        ObservableList<Cliente> ClientesOb = FXCollections.observableArrayList(Clientes);
        LstClientes.setItems(ClientesOb);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atualizarLista();
    }
}
