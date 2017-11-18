<?php 
defined('BASEPATH') OR exit('No direct script access allowed');

class Empresa_model extends CI_Model {
	public $table = "empresa";

	function __construct() {
		parent::__construct();
		$this->load->model('basic/Empresa_basic');
	}

	public function cadastrar($empresa) {
		$data = array(
			'razao_social' 	=> $empresa->getRazaoSocial(),
			'cnpj' 		 	=> $empresa->getCnpj(),
			'logradouro' 	=> $empresa->getLogradouro(),
			'numero' 		=> $empresa->getNumero(),
			'nome_fantasia' => $empresa->getNomeFantasia(),
			'complemento' 	=> $empresa->getComplemento(),
			'bairro' 		=> $empresa->getBairro(),
			'cidade' 		=> $empresa->getCidade(),
			'uf' 			=> $empresa->getUf(),
			'cep' 			=> $empresa->getCep(),
			'latitude' 		=> $empresa->getLatitude(),
			'longitude' 	=> $empresa->getLongitude(),
			'situacao'		=> $empresa->getSituacao()
		);

		$this->db->insert($this->table, $data);
		if(!$this->db->affected_rows()) {
			return false;
		}
		return $this->db->insert_id();
	}

	public function listar() {
		$this->db->select('*')->from($this->table);
		$query = $this->db->get();

		if($query->num_rows() > 0) {
			$result = $query->result();
			$empresas = array();
			foreach($result as $row) {
				$empresa = new Empresa_basic();

				$empresa->setCodigo($row->codigo);
				$empresa->setRazaoSocial($row->razao_social);
				$empresa->setCnpj($row->cnpj);
				$empresa->setLogradouro($row->logradouro);
				$empresa->setNumero($row->numero);
				$empresa->setNomeFantasia($row->nome_fantasia);
				$empresa->setComplemento($row->complemento);
				$empresa->setBairro($row->bairro);
				$empresa->setCidade($row->cidade);
				$empresa->setUf($row->uf);
				$empresa->setCep($row->cep);
				$empresa->setLatitude($row->latitude);
				$empresa->setLongitude($row->longitude);
				$empresa->setSituacao($row->situacao);

				$empresas[] = $empresa;
			}

			return $empresas;
		} else {
			return false;
		}
	}

	public function empresa_motorista($motorista) {
		$this->db->select('
			empresa.codigo AS codigo, 
			empresa.razao_social AS razao_social, 
			empresa.cnpj AS cnpj, 
			empresa.logradouro AS logradouro, 
			empresa.numero AS numero, 
			empresa.nome_fantasia AS nome_fantasia, 
			empresa.complemento AS complemento, 
			empresa.bairro AS bairro, 
			empresa.cidade AS cidade, 
			empresa.uf AS uf, 
			empresa.cep AS cep, 
			empresa.latitude AS latitude, 
			empresa.longitude AS longitude, 
			empresa.foto AS foto, 
			empresa.situacao AS situacao
		')->from($this->table);
		$this->db->join('empresa_motorista', 'empresa_motorista.cod_empresa = '.$this->table.'.codigo');
		$this->db->join('motorista', 'motorista.codigo = empresa_motorista.cod_motorista', 'left');
		$this->db->where('motorista.codigo', $motorista);
		$this->db->order_by($this->table.'.razao_social', 'ASC');
		$query = $this->db->get();

		if($query->num_rows() > 0) {
			$result = $query->result();
			$empresas = array();
			foreach($result as $row) {
				$empresa = new Empresa_basic();

				$empresa->setCodigo($row->codigo);
				$empresa->setRazaoSocial($row->razao_social);
				$empresa->setCnpj($row->cnpj);
				$empresa->setLogradouro($row->logradouro);
				$empresa->setNumero($row->numero);
				$empresa->setNomeFantasia($row->nome_fantasia);
				$empresa->setComplemento($row->complemento);
				$empresa->setBairro($row->bairro);
				$empresa->setCidade($row->cidade);
				$empresa->setUf($row->uf);
				$empresa->setCep($row->cep);
				$empresa->setLatitude($row->latitude);
				$empresa->setLongitude($row->longitude);
				$empresa->setFoto($row->foto);
				$empresa->setSituacao($row->situacao);

				$empresas[] = $empresa;
			}

			return $empresas;
		} else {
			return false;
		}
	} 

		public function disponibilidade_empresa($motorista) {
		$this->db->select('
			empresa.codigo AS codigo, 
			empresa.razao_social AS razao_social, 
			empresa.cnpj AS cnpj, 
			empresa.logradouro AS logradouro, 
			empresa.numero AS numero, 
			empresa.nome_fantasia AS nome_fantasia, 
			empresa.complemento AS complemento, 
			empresa.bairro AS bairro, 
			empresa.cidade AS cidade, 
			empresa.uf AS uf, 
			empresa.cep AS cep, 
			empresa.latitude AS latitude, 
			empresa.longitude AS longitude, 
			empresa.foto AS foto, 
			empresa.situacao AS situacao
		')->from($this->table);
		$this->db->join('disponibilidade_empresa', 'disponibilidade_empresa.cod_empresa = '.$this->table.'.codigo');
		$this->db->join('motorista', 'motorista.disponibilidade = disponibilidade_empresa.disponibilidade', 'left');
		$this->db->where('motorista.codigo', $motorista);
		$this->db->order_by($this->table.'.razao_social', 'ASC');
		$query = $this->db->get();

		if($query->num_rows() > 0) {
			$result = $query->result();
			$empresas = array();
			foreach($result as $row) {
				$empresa = new Empresa_basic();

				$empresa->setCodigo($row->codigo);
				$empresa->setRazaoSocial($row->razao_social);
				$empresa->setCnpj($row->cnpj);
				$empresa->setLogradouro($row->logradouro);
				$empresa->setNumero($row->numero);
				$empresa->setNomeFantasia($row->nome_fantasia);
				$empresa->setComplemento($row->complemento);
				$empresa->setBairro($row->bairro);
				$empresa->setCidade($row->cidade);
				$empresa->setUf($row->uf);
				$empresa->setCep($row->cep);
				$empresa->setLatitude($row->latitude);
				$empresa->setLongitude($row->longitude);
				$empresa->setFoto($row->foto);
				$empresa->setSituacao($row->situacao);

				$empresas[] = $empresa;
			}

			return $empresas;
		} else {
			return false;
		}
	} 
}