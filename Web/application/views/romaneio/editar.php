<style type="text/css">
	.seq { color: #999; }
	.seq-icon { color: #FFF; transition: 0.3s; }
	.panel-group .panel .panel-heading { cursor: grab; }
	.panel-group .panel .panel-heading h2 { cursor: grab; }
	.panel-group .panel .panel-heading:hover .seq-icon { color: #999; }
</style>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
$(function() {
	$("#sortable").sortable({
		create: function(event, ui){ $("#order").val($(this).sortable('serialize')); },
		stop: function(event, ui){ $("#order").val($(this).sortable('serialize')); }
	});
	$("#sortable").disableSelection();
});
</script>

<div class="content" style="width: 55%; float: left">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="card">
                    <div class="card-header card-header-tabs" data-background-color="blue-center">
                        <div class="nav-tabs-navigation">
                            <div class="nav-tabs-wrapper">
                                <ul class="nav nav-tabs" data-tabs="tabs" style="background: none">
                                    <li class="<?= (!is_null($this->session->flashdata('entrega')))? '' : 'active' ?>">
                                        <a data-toggle="tab" href="#profile" aria-expanded="true">
                                            <i class="material-icons">local_shipping</i> Romaneio
                                            <div class="ripple-container"></div>
                                        </a>
                                    </li>
                                    <li class="<?= (!is_null($this->session->flashdata('entrega')))? 'active' : '' ?>">
                                        <a data-toggle="tab" href="#entregas" aria-expanded="true">
                                            <i class="material-icons">content_paste</i> Entregas
                                            <div class="ripple-container"></div>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="card-content">
                        <div class="tab-content">
                            <div class="tab-pane <?= (!is_null($this->session->flashdata('entrega')))? '' : 'active' ?>" id="profile">
                        		<form action="<?= base_url().'romaneio/editar' ?>" method="post">
                    			<input type="hidden" name="codigo" value="<?= $romaneio[0]->codigo; ?>">
                    			<input type="hidden" name="codigo_motorista" value="<?= $romaneio[0]->motorista->codigo; ?>">
                            	<div class="row">
									<div class="col-md-2 lm15">
										<div class="form-group label-floating">
											<label class="control-label">Romaneio</label>
											<input type="text" class="form-control" value="<?= $romaneio[0]->codigo; ?>" autocomplete="off" disabled>
										</div>
									</div>
									<div class="col-md-7 lm15">
										<div class="form-group label-floating">
											<label class="control-label">Estabelecimento</label>
											<select class="form-control estabelecimento" name="estabelecimento">
												<?php foreach($estabelecimento as $row): ?>
													<option class="option" value="<?= $row->codigo ?>|<?= $row->logradouro.", ".$row->numero." - ".$row->bairro ?>" <?= ($row->codigo == $romaneio[0]->estabelecimento->codigo) ? 'selected' : '' ?>>
														<?= $row->razao_social." — ".$row->bairro; ?>
													</option>
												<?php endforeach; ?>
											</select>
										</div>
									</div>
									<div class="col-md-3 lm15">
										<div class="form-group label-floating">
											<label class="control-label">Preço</label>
											<input type="text" id="valor" class="form-control" name="valor" autocomplete="off" value="<?= $romaneio[0]->valor ?>">
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-4 lm15">
										<div class="form-group">
											<label>Transportadora</label>
											<select class="form-control transportadora" name="transportadora">
												<option class="option-undefined" value="0" <?= ($romaneio[0]->estabelecimento->codigo == NULL) ? 'selected' : '' ?>>Estabelecimento</option>
												<?php foreach($transportadora as $row): ?>
													<option class="option" value="<?= $row->codigo ?>" <?= ($row->codigo == $romaneio[0]->transportadora->codigo) ? 'selected' : '' ?>>
														<?= $row->nome_fantasia; ?>
													</option>
												<?php endforeach; ?>
											</select>
										</div>
									</div>
									<div class="col-md-4 lm15">
										<div class="form-group">
											<label>Motorista</label>
											<select class="form-control motorista" name="motorista">
												<option class="option-undefined undefined" value="0" <?= ($romaneio[0]->motorista->codigo == NULL) ? 'selected' : '' ?>>Indefinido</option>
												<?php if(!is_null($romaneio[0]->motorista->codigo)): ?>
													<optgroup label="Atual">
													<?php
														foreach($motorista as $row):
															$nome = explode(" ", $row->nome);
													?>
														<option class="option" value="<?= $row->codigo ?>" <?= ($row->codigo == $romaneio[0]->motorista->codigo) ? 'selected' : '' ?>>
															<?= $nome[0]." ".end($nome); ?>
														</option>
													<?php endforeach; ?>
													</optgroup>
												<?php endif; ?>
												<optgroup label="Disponíveis">
												<?php
													foreach($motorista_disponivel as $row):
														$nome = explode(" ", $row->nome);
												?>
													<option class="option" value="<?= $row->codigo ?>">
														<?= $nome[0]." ".end($nome); ?>
													</option>
												<?php endforeach; ?>
												</optgroup>
											</select>
										</div>
									</div>
									<div class="col-md-4 lm15">
										<div class="form-group">
											<label>Tipo do Veículo</label>
											<select class="form-control" name="tipoveiculo">
												<?php foreach($tipoveiculo as $row): ?>
													<option class="option" value="<?= $row->codigo ?>" <?= ($row->codigo == $romaneio[0]->tipo_veiculo->codigo) ? 'selected' : '' ?>>
														<?= $row->descricao; ?>
													</option>
												<?php endforeach; ?>
											</select>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<a href="<?= base_url().'romaneio/imprimir/'.$romaneio[0]->codigo ?>">
										<span type="submit" class="btn btn-danger btn-simple btn-fill pull-left f12 upper">
											Imprimir
										</span>
										</a>
										<button type="submit" name="editar" class="btn btn-danger btn-fill pull-right f12 upper">Salvar</button>
									</div>
								</div>
                            	</form>
                            </div>
                            <div class="tab-pane <?= (!is_null($this->session->flashdata('entrega')))? 'active' : '' ?>" id="entregas">
                            	<form action="<?= base_url().'entrega/editar'; ?>" method="post">
                            	<input type="hidden" name="order" id="order">
                            	<input type="hidden" name="romaneio" value="<?= $this->uri->segment(3); ?>">
                            	<div id="sortable">
									<?php
										$i = 0;
										foreach($entrega as $row):
											$ult_entrega = $row->seq_entrega;
											$i++;
									?>
									<input type="hidden" name="i-<?= $i ?>" value="<?= $i; ?>">
									<input type="hidden" name="seq-entrega-<?= $i; ?>" value="<?= $row->seq_entrega; ?>">
									<div aria-multiselectable="true" class="panel-group" id="set_<?= $i; ?>" role="tablist"><!-- id="accordion"   invés de   id="set_<?= $i; ?>" -->
										<div class="panel panel-default">
											<div class="panel-heading" id="headingOne" role="tab" >
												<a aria-controls="collapseOne" aria-expanded="true" data-parent="#accordion" data-toggle="collapse" href="#collapse<?= $i; ?>" role="button" class="">
													<h2 class="panel-title">
														<?= $row->destinatario->razao_social; ?> — <?= $row->destinatario->bairro; ?>, <?= $row->destinatario->cidade ?>
														<?php if(count($entrega) >= 2): ?>
														<span class="pull-right">
															<span class="seq">
																<?= $i; ?><!-- ?= $row->seq_entrega; ? -->
															</span>&nbsp;
															<span class="seq-icon">
																<i class="fa fa-arrows-v" aria-hidden="true"></i>
															</span>
														</span>
														<?php endif; ?>
													</h2>
												</a>
											</div>
											<div aria-labelledby="headingOne" class="panel-collapse collapse" id="collapse<?= $i; ?>" role="tabpanel" aria-expanded="true" style="">
												<div class="panel-body">
													<div class="row">
														<div class="col-md-12 lm15">
															<div class="form-group">
																<label>Destinatário <?= $i; ?> <!-- ?= $row->seq_entrega; ? --></label>
																<select class="form-control" name="destinatario-<?= $i; ?>">
																	<?php foreach($destinatario as $raw): ?>
																		<option class="option" value="<?= $row->destinatario->codigo ?>" <?= ($row->destinatario->codigo == $raw->codigo)? 'selected' : '' ?>><?= $raw->razao_social." — ".$raw->bairro.", ".$raw->cidade; ?></option>
																	<?php endforeach; ?>
																</select>
															</div>
														</div>
													</div>
													<div class="row">
														<?php $peso_carga = explode(" ", $row->peso_carga); ?>
														<div class="col-md-3 lm15">
															<div class="form-group">
																<label>Peso da Carga</label>
																<input type="text" pattern="[0-9]+$" name="peso_carga-<?= $i; ?>" value="<?= $peso_carga[0]; ?>" class="form-control" autocomplete="off">
															</div>
														</div>
														<div class="col-md-3 lm15">
															<div class="form-group">
																<label>Medida</label>
																<select class="form-control" name="medida-<?= $i; ?>">
																	<option class="option" value="kg" <?= ($peso_carga[1] == 'kg')? 'selected' : '' ?>>Quilograma</option>
																	<option class="option" value="t" <?= ($peso_carga[1] == 't')? 'selected' : '' ?>>Tonelada</option>
																	<option class="option" value="hg" <?= ($peso_carga[1] == 'hg')? 'selected' : '' ?>>Hectograma</option>
																</select>
															</div>
														</div>
														<div class="col-md-3 lm15">
															<div class="form-group">
																<label>Nota Fiscal</label>
																<input type="text" pattern="[0-9]+$" name="nota_fiscal-<?= $i; ?>" value="<?= $row->nota_fiscal; ?>" maxlength="7" class="form-control" autocomplete="off">
															</div>
														</div>
														<div class="col-md-3 lm15">
															<div class="form-group">
																<label>Status</label>
																<input type="text" name="pesocarga" value="<?= $row->status_entrega->descricao; ?>" class="form-control" autocomplete="off" disabled>
															</div>
														</div>
													</div>
													<div class="row">
														<?php if(count($entrega) >= 2): ?>
														<div class="col-md-12">
															<a href="<?= base_url().'entrega/excluir/'.$row->seq_entrega.'/'.$row->romaneio->codigo ?>">
																<span class="btn btn-danger btn-fill btn-simple pull-left f12 upper">
																	<i class="material-icons">delete</i>
																	Excluir
																</span>
															</a>
														</div>
														<?php endif; ?>
													</div>
												</div>
											</div>
										</div>
									</div>
									<?php endforeach; ?>
								</div>
								<div class="row btn-footer">
									<div class="col-md-12">
										<span class="btn btn-danger btn-round btn-fab btn-simple btn-add pull-right f12 upper pull-left" rel="tooltip" title="Adicionar" data-placement="right">
											<i class="material-icons" style="font-size: 25px;">add</i>
										</span>
										<button type="submit" name="editar" class="btn btn-danger btn-fill btn-salvar pull-right f12 upper">Salvar</button>
									</div>
								</div>
								</form>

								<div id="add" style="display: none;">
									<form action="<?= base_url().'entrega/cadastrar' ?>" method="post">
										<input type="hidden" name="romaneio" value="<?= $this->uri->segment(3); ?>">
										<input type="hidden" name="entrega" value="<?= ++$ult_entrega; ?>">
										<div class="row">
											<div class="col-md-3 lm15">
												<div class="form-group label-floating">
													<label class="control-label">Romaneio</label>
													<input type="hidden" name="entrega1" value="entrega1">
													<input type="text" value="<?= $this->uri->segment(3); ?>" class="form-control" disabled>
												</div>
											</div>
											<div class="col-md-9 lm15">
												<div class="form-group label-floating">
													<label class="control-label">Destinatário</label>
													<select class="form-control" name="destinatario" ng-model="destinatario">
														<option value="" class="option_none" disabled selected></option>
														<?php foreach($destinatario as $row): ?>
															<option class="option" value="<?= $row->codigo ?>|<?= $row->logradouro.", ".$row->numero." - ".$row->bairro ?>">
																<?= $row->razao_social." — ".$row->bairro.", ".$row->cidade; ?>
															</option>
														<?php endforeach; ?>
													</select>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-3 lm15">
												<div class="form-group label-floating">
													<label class="control-label">Peso da Carga</label>
													<input type="text" ng-model="peso_carga" pattern="[0-9]+$" class="form-control" name="peso_carga" required>
												</div>
											</div>
											<div class="col-md-3 lm15">
												<div class="form-group label-floating">
													<label class="control-label">Medida</label>
													<select ng-model="medida" class="form-control" name="medida" required>
														<option value="" class="option_none" disabled selected></option>
														<option value="kg">Quilograma</option>
														<option value="t">Tonelada</option>
														<option value="hg">Hectograma</option>
													</select>
												</div>
											</div>
											<div class="col-md-6 lm15">
												<div class="form-group label-floating">
													<label class="control-label">Nota Fiscal</label>
													<input type="text" pattern="[0-9]+$" class="form-control add-nota-fiscal" name="nota_fiscal" minlength="7" maxlength="7">
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-12">
												<span class="btn btn-danger btn-round btn-fab btn-simple btn-close pull-left f12 upper pull-left" rel="tooltip" title="Fechar" data-placement="right">
													<i class="material-icons" style="font-size: 25px;">close</i>
												</span>
												<button type="submit" class="btn btn-danger btn-fill btn-salvar pull-right f12 upper" ng-disabled="!destinatario || !peso_carga || !medida">Adicionar</button>
											</div>
										</div>
									</form>
								</div>
							</div>
                        </div>
                    </div>
                </div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="card">
					<div class="card-header" data-background-color="blue-center">
						<h4 class="title">Rota de Entregas</h4>
						<p class="category">Itinerário do Romaneio <?= $romaneio[0]->codigo ?></p>
					</div>
					<div class="card-content table-responsive" style="height: 350px; overflow-x: auto;" id="trajeto">
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="map" style="width: 45%"></div>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.0/jquery.mask.js"></script>
<script type="text/javascript">
	function initMap() {
		var directionsService = new google.maps.DirectionsService;
		var directionsDisplay = new google.maps.DirectionsRenderer;
		var map = new google.maps.Map(document.getElementById('map'), {
			center: {lat: -8.085493, lng: -34.8877151},
			zoom: 17
		});
		directionsDisplay.setMap(map);
		directionsDisplay.setPanel(document.getElementById('trajeto'));

		calculateAndDisplayRoute(directionsService, directionsDisplay);
		marker.setPosition(location);
	}

	function calculateAndDisplayRoute(directionsService, directionsDisplay) {
		var waypts = [];
		<?php
			$fim = array_pop($entrega);
			if(count($entrega) >= 1):
				foreach($entrega as $row):
		?>
			waypts.push({
				location: '<?= $row->destinatario->logradouro.", ".$row->destinatario->numero." - ".$row->destinatario->bairro ?>',
				stopover: true
			});
		<?php
				endforeach;
			endif;
		?>

		directionsService.route({
			origin: '<?= $romaneio[0]->estabelecimento->logradouro.", ".$romaneio[0]->estabelecimento->numero." - ".$romaneio[0]->estabelecimento->bairro ?>',
			destination: '<?= $fim->destinatario->logradouro.", ".$fim->destinatario->numero." - ".$fim->destinatario->bairro ?>',
			waypoints: waypts,
			optimizeWaypoints: true,
			travelMode: 'DRIVING'
		}, function(response, status) {
			if (status === 'OK') {
				directionsDisplay.setDirections(response);
			} else {
				window.alert('A solicitação de instruções falhou devido a ' + status);
			}
		});
	}

	$('.add-nota-fiscal').blur(function(){
		var nota_fiscal = $('.add-nota-fiscal').val();
		if(nota_fiscal.length == 7) {
			$.ajax({
				url: '<?= base_url() ?>entrega/verificar',
				type: 'POST',
				data: 'nota_fiscal='+nota_fiscal,
				dataType: 'json',
				success: function(data) {
					if(data == false) {
						demo.showNotification('bottom', 'right', 'Nota Fiscal já cadastrado, tente outro NFS.');
						$('.add-nota-fiscal').val("");
						$('.add-nota-fiscal').focus();
					}
				}
			});

			return true;
		}
	});

	$(".transportadora").change(function() {
		var transportadora = $('.transportadora').val();
		if(transportadora == '0') {
			$(".undefined").css("display", "block");
		} else {
			$(".undefined").css("display", "none");
		}
	});

	$(".btn-add").click(function() {
		$("#sortable").hide();
		$(".btn-footer").hide();
		$("#add").fadeIn();
	});

	$(".btn-close").click(function() {
		$("#add").hide();
		$("#sortable").fadeIn();
		$(".btn-footer").fadeIn();
	});

	$(document).ready(function(){
		var transportadora = $('.transportadora').val();
		if(transportadora == '0') {
			$(".undefined").css("display", "block");
		} else {
			$(".undefined").css("display", "none");
		}

		$('#valor').mask('000.000.000.000.000,00', {reverse: true});
	});
</script>

<!-- https://developers.google.com/maps/documentation/javascript/examples/directions-waypoints?hl=pt-br -->