<link rel="stylesheet" href="<?= base_url(); ?>assets/css/pop-up.css" />
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
<script type="text/javascript">
	function exclusao(codigo) {
		$('.cd-popup').addClass('is-visible');
		$('.btn-confirm-yes').on('click', function(event){
			event.preventDefault();
			window.location.replace("<?= base_url() ?>estabelecimento/excluir/"+codigo);
			return true;
		});
		$('.btn-confirm-no').on('click', function(event){
			event.preventDefault();
			$('.cd-popup').removeClass('is-visible');
			return false;
		});
		$('.cd-popup').on('click', function(event){
			if($(event.target).is('.cd-popup-close') || $(event.target).is('.cd-popup')) {
				event.preventDefault();
				$(this).removeClass('is-visible');
			}
		});
		return false;
	}
</script>

<div class="cd-popup" role="alert">
	<div class="cd-popup-container">
		<p><?= $this->session->userdata('nome') ?>, tem certeza que quer<br> excluir este Estabelecimento ?</p>
		<ul class="cd-buttons">
			<li><a href="#" class="btn-confirm-yes">Sim</a></li>
			<li><a href="#" class="btn-confirm-no">Não</a></li>
		</ul>
		<a href="#" class="cd-popup-close img-replace"></a>
	</div>
</div>

<div class="content">
	<div class="container-fluid">
		<div class="row">
			<div id="konami" align="center" style="display: none;"><img src="<?= base_url(); ?>assets/img/others/unnamed.jpg"></div>
			<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-text" data-background-color="blue-left">
						<h4 class="card-title" style="font-size: 18px">Listagem</h4>
					</div>
					<div class="card-content table-responsive">
						<table class="table table-hover" id="tables">
							<thead class="text-primary">
								<th class="th-desc">
									CNPJ
									<span style="opacity: 0.7;">
										<i class="fa fa-sort" aria-hidden="true"></i>
									</span>
								</th>
								<th class="th-desc">
									Razão Social
									<span style="opacity: 0.7;">
										<i class="fa fa-sort" aria-hidden="true"></i>
									</span>
								</th>
								<th class="th-desc">
									Logradouro
									<span style="opacity: 0.7;">
										<i class="fa fa-sort" aria-hidden="true"></i>
									</span>
								</th>
								<th class="th-desc">
									Bairro
									<span style="opacity: 0.7;">
										<i class="fa fa-sort" aria-hidden="true"></i>
									</span>
								</th>
								<th class="th-desc">
									Cidade
									<span style="opacity: 0.7;">
										<i class="fa fa-sort" aria-hidden="true"></i>
									</span>
								</th>
								<th></th>
							</thead>
							<tbody>
								<?php
									if($estabelecimento != FALSE):
										foreach($estabelecimento as $row):
								?>
								<tr>
									<td><?= $row->cnpj; ?></td>
									<td><?= $row->razao_social; ?></td>
									<td>
										<a href="<?= base_url().'home/mapa?endereco='.str_replace(" ", "+", acentuacao($row->logradouro)).','.$row->numero.'-'.str_replace(" ", "+", acentuacao($row->bairro)); ?>">
											<?= $row->logradouro; ?><?= $row->complemento; ?>, <?= $row->numero; ?>
										</a>
									</td>
									<td><?= $row->bairro; ?></td>
									<td><?= $row->cidade; ?></td>
									<td>
										<?php if($this->session->userdata('perfil') == 'A'): ?>
										<a href="<?= base_url().'estabelecimento/excluir/'.$row->codigo; ?>" onclick="return exclusao('<?= $row->codigo ?>')" rel="tooltip" data-placement="left" title="Excluir" class="btn-pattern">
											<i class="fa fa-times"></i>
										</a>
										<?php endif; ?>
									</td>
								</tr>
								<?php
										endforeach;
									else:
								?>
									<tr align="center">
										<td colspan="7" class="desc f10 upper" style="font-size: 12px">Nenhum Estabelecimento encontrado :(</td>
									</tr>
								<?php endif; ?>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="2">
										<span class="desc f12" style="padding: 15px;">
											<?php
												if($this->uri->segment(2) == 's') {
													echo count($estabelecimento).' Estabelecimento(s) — '.str_replace("_", " ", $this->input->get('filtro')).': '.$this->input->get('procurar');
												} else {
													$count = ($estabelecimento != FALSE)? count($estabelecimento) : '0';
													echo $count.' Estabelecimento(s) totais';
												}
											?>
										</span>
									</td>
									<td colspan="2">
										<?= ($this->uri->segment(2) == 's') ? "<small class='desc'><a href='".base_url()."estabelecimento'>voltar</a></small> " : $pagination; ?>
									</td>
									<td colspan="2">
										<?php if($this->session->userdata('perfil') == 'A'): ?>
											<a href="<?= base_url().'estabelecimento/add' ?>">
												<button type="submit" class="btn btn-simple btn-danger btn-fill pull-right f12 upper">Adicionar</button>
											</a>
										<?php endif; ?>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>