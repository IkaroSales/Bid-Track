<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Romaneio extends CI_Controller {
	public function index() {
		$data['middle'] = 'romaneio';
		$this->load->view('pattern/layout', $data);
	}

	public function integracao() {
		$data['middle'] = 'romaneio/integracao';
		$this->load->view('pattern/layout', $data);
	}

	public function mapa() {
		$address = trim(str_replace(" ", "+", $this->input->get('endereco')));
		$json_url = "https://maps.googleapis.com/maps/api/geocode/json?address=$address&key=AIzaSyDyIn0nbXxWrWrdyV9plcwTO_bJ-Rm9y7w";
		$json = file_get_contents(str_replace("&amp;", "&", $json_url));
		$coordenadas = json_decode($json, TRUE);

		$data['coordenadas'] = $coordenadas;
		$data['middle'] = 'mapa';
		$this->load->view('pattern/layout', $data);
	}

	public function visualizar() {
		$data['middle'] = 'romaneio/visualizar';
		$this->load->view('pattern/layout', $data);
	}

	public function integrar(){
		$nome = 'bidtrack-'.rand(0, 500000);
		$arquivo = $_FILES['arquivo'];
		$configuracao = array(
			'upload_path' => './assets/romaneios/',
			'allowed_types' => 'txt|csv',
			'file_name' => $nome.'.csv',
			'max_size' => '5000'
		);

		$this->load->library('upload');
		$this->upload->initialize($configuracao);
		if(!$this->upload->do_upload('arquivo')) {
			$data['info'] = $this->upload->display_errors();
		} else {
			$data['info'] = 'Arquivo salvo com sucesso.';
		}

		$handle = fopen("./assets/romaneios/".$nome.".csv", "r");
		$entrega = array();
		while(!feof($handle)) {
			$linha = trim(fgets($handle, 4096));
			$column = explode(";", $linha);
			
			$entrega[] = $column;
		}
		fclose($handle);

		$romaneio = array_shift($entrega);

		$data['entrega'] = $entrega;
		$data['romaneio'] = $romaneio;
		$data['middle'] = 'romaneio/integracao';
		$this->load->view('pattern/layout', $data);
	}
}