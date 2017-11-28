<style type="text/css">
	.lm10 { margin-top: -10px; }
</style>

<div class="content">
	<div class="container-fluid">
		<div class="row">
			<div id="row-estabelecimento" class="col-md-12" style="transition: 0.5s;">
				<div class="card">
					<div class="card-header" data-background-color="blue-left">
						<h4 class="title">Estabelecimento</h4>
						<p class="category">Cadastre o <span id="empresa-title">Estabelecimento</span></p>
					</div>
					<div class="card-content">
						<form action="<?= base_url() ?>estabelecimento/cadastrar" method="post">
							<div class="row">
								<div class="col-md-3">
									<div id="label-cnpj" class="form-group label-floating">
										<label class="control-label">CNPJ</label>
										<input type="text" name="cnpj" id="cnpj" ng-model="cnpj" pattern="([0-9]{2}[\.]?[0-9]{3}[\.]?[0-9]{3}[\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\.]?[0-9]{3}[\.]?[0-9]{3}[-]?[0-9]{2})" class="form-control lm10" autocomplete="off" required>
									</div>
								</div>
								<div class="col-md-6">
									<div id="label-razao" class="form-group label-floating">
										<label class="control-label">Razão Social</label>
										<input type="text" name="razao_social" pattern="[a-zA-ZÁÉÍÓÚáéíóúÃÕãõÂÊÎÔÛâêîôûÀÈÌÒÙàèìòùÇç.,/\s]+$" id="empresa-input" class="form-control lm10" autocomplete="off" disabled required>
									</div>
								</div>
								<div class="col-md-2">
									<div id="label-cep" class="form-group label-floating">
										<label class="control-label">CEP</label>
										<input type="text" name="cep" id="cep" autocomplete="off" class="form-control lm10" autocomplete="off" disabled required>
									</div>
								</div>
								<div class="col-md-1">
									<div id="label-uf" class="form-group label-floating">
										<label class="control-label">UF</label>
										<input type="text" name="uf" pattern="[a-zA-Z\s]+$" maxlength="2" id="uf" class="form-control upper lm10" required disabled>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3 lm10">
									<div id="label-endereco" class="form-group label-floating">
										<label class="control-label">Endereço</label>
										<input type="text" name="logradouro" pattern="[a-zA-ZÁÉÍÓÚáéíóúÃÕãõÂÊÎÔÛâêîôûÀÈÌÒÙàèìòùÇç\s]+$" id="endereco" class="form-control lm10" autocomplete="off" disabled required>
									</div>
								</div>
								<div class="col-md-2 lm10">
									<div id="label-complemento" class="form-group label-floating">
										<label class="control-label">Compl.</label>
										<input type="text" name="complemento" pattern="[a-zA-ZÁÉÍÓÚáéíóúÃÕãõÂÊÎÔÛâêîôûÀÈÌÒÙàèìòùÇç/\s]+$" id="complemento" class="form-control lm10" autocomplete="off" disabled>
									</div>
								</div>
								<div class="col-md-1 lm10">
									<div id="label-numero" class="form-group label-floating">
										<label class="control-label">Número</label>
										<input type="text" name="numero" pattern="[0-9]+$" id="numero" autocomplete="off" class="form-control lm10" disabled required>
									</div>
								</div>
								<div class="col-md-3 lm10">
									<div id="label-bairro" class="form-group label-floating">
										<label class="control-label">Bairro</label>
										<input type="text" name="bairro" pattern="[a-zA-ZÁÉÍÓÚáéíóúÃÕãõÂÊÎÔÛâêîôûÀÈÌÒÙàèìòùÇç\s]+$" id="bairro" autocomplete="off" class="form-control lm10" disabled required>
									</div>
								</div>
								<div class="col-md-3 lm10">
									<div id="label-cidade" class="form-group label-floating">
										<label class="control-label">Cidade</label>
										<input type="text" name="cidade" pattern="[a-zA-ZÁÉÍÓÚáéíóúÃÕãõÂÊÎÔÛâêîôûÀÈÌÒÙàèìòùÇç\s]+$" id="cidade" class="form-control lm10" autocomplete="off" disabled required>
									</div>
								</div>
								
							</div>
							<div class="row">
								<div class="col-md-6">
									<a href="<?= base_url('estabelecimento'); ?>" class="btn btn-danger btn-simple">
										Voltar
									</a>
								</div>
								<div class="col-md-6">
									<button class="btn btn-danger pull-right" ng-disabled="!cnpj" type="submit">
										Cadastrar
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>

			<div id="row-estabelecimento-info" class="col-md-4" style="display: none;">
				<div class="card">
					<div class="card-header" data-background-color="blue-center">
						<h4 class="title">Informações</h4>
						<p class="category">Detalhamento do Estabelecimento</p>
					</div>
					<div class="card-content">
						<div class="row" id="transportadora-info">
							<div class="col-md-12" align="center">
								<div class="image"></div>
								<div class="content">
									<h4 id="empresa"></h4>
									<p id="atividade" class="description text-center"></p>
									<table class="table table-hover">
										<tr>
											<td>Abertura</td>
											<td id="abertura"></td>
										</tr>
										<tr>
											<td>Tipo da Empresa</td>
											<td id="tipo"></td>
										</tr>
										<tr>
											<td>Situação</td>
											<td id="situacao"></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
						<!-- div class="row" id="sem-info">
							<div class="col-md-12" style="padding: 15px;" align="center">
								<span style="color: #999;">Por favor, preencha o CNPJ.</span>
							</div>
						</div -->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.0/jquery.mask.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#cnpj').mask('00.000.000/0000-00');
		$('#cep').mask('00000-000');
		$('#telefone').mask('00 0000-0000');
		$('#cnpj').focus();
	});

	$('#cnpj').blur(function() {
		$.ajax({
			url: '<?= base_url() ?>empresa/cnpj',
			type: 'POST',
			data: 'cnpj='+$('#cnpj').val(),
			dataType: 'json',
			success: function(data) {
				$('#row-estabelecimento').removeClass('col-md-12').addClass('col-md-8');
				window.setTimeout(function(){
					$('#row-estabelecimento-info').fadeIn('low');
				}, 500);

				$('.image').html("<a href='<?= base_url() ?>home/mapa?endereco="+data.mapa+"' target='_blank'><img src='https://maps.googleapis.com/maps/api/staticmap?center="+data.mapa+"&zoom=18&scale=1&size=600x300&maptype=roadmap&key=AIzaSyDQjgSKcQEHV6GY-_TL3vxbEwZ6rYG7LVA&format=png&visual_refresh=true&markers=icon:http://i.imgur.com/jRfjvrz.png%7Cshadow:true%7C"+data.mapa+"' style='border-radius:3.5px' alt='"+data.endereco+"' title='"+data.endereco+"'></a>");
				$('#empresa').text(data.nome);
				$('#empresa-title').text(data.nome);
				$('#tipo').text(data.tipo);
				$('#situacao').text(data.situacao);
				$('#atividade').text(data.atividade);
				$('#abertura').text(data.abertura);
				
				$('#empresa-input').val(data.nome);
				$('#endereco').val(data.endereco);
				$('#numero').val(data.numero);
				$('#complemento').val(data.complemento);
				$('#bairro').val(data.bairro);
				$('#cidade').val(data.cidade);
				$('#cep').val(data.cep);
				$('#uf').val(data.uf);
				$('#email').val(data.email);
				
				$('#label-razao').removeClass('is-empty');
				$('#label-endereco').removeClass('is-empty');
				$('#label-complemento').removeClass('is-empty');
				$('#label-numero').removeClass('is-empty');
				$('#label-cep').removeClass('is-empty');
				$('#label-bairro').removeClass('is-empty');
				$('#label-cidade').removeClass('is-empty');
				$('#label-uf').removeClass('is-empty');
			}, error: function(result) {
				if($("#cnpj").val() != '') {
					demo.showNotification('bottom', 'right', 'Desculpe, CNPJ não encontrado na Receita Federal.');
				}

				$('#empresa-input').val("").prop('disabled', true);
				$('#endereco').val("").prop('disabled', true);
				$('#numero').val("").prop('disabled', true);
				$('#complemento').val("").prop('disabled', true);
				$('#bairro').val("").prop('disabled', true);
				$('#cidade').val("").prop('disabled', true);
				$('#cep').val("").prop('disabled', true);
				$('#uf').val("").prop('disabled', true);
				$('#email').val("").prop('disabled', true);

				$('#label-razao').addClass('is-empty');
				$('#label-endereco').addClass('is-empty');
				$('#label-complemento').addClass('is-empty');
				$('#label-numero').addClass('is-empty');
				$('#label-cep').addClass('is-empty');
				$('#label-bairro').addClass('is-empty');
				$('#label-cidade').addClass('is-empty');
				$('#label-uf').addClass('is-empty');

				$('#empresa-title').text("Estabelecimento");
				$('#row-estabelecimento-info').fadeOut('fast');
				window.setTimeout(function(){
					$('#row-estabelecimento').removeClass('col-md-8').addClass('col-md-12');
				}, 500);
			}
		});
		return false;
	});
</script>