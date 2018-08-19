CREATE TABLE formularios (descricao character varying(2000) NOT NULL);
INSERT INTO formularios (descricao) VALUES ('br.gov.pb.joaopessoa.rh.model.entities.GroupAuthentication');
INSERT INTO formularios (descricao) VALUES ('br.gov.pb.joaopessoa.rh.model.pojos.Usuario');
INSERT INTO formularios (descricao) VALUES ('br.gov.pb.joaopessoa.rh.model.pojos.Pessoa');


INSERT INTO group_authentication (id_group_authentication, name, ativo) 
SELECT nextval('seq_group_authentication'),
       'Administrador',
       true
Where Not Exists(Select 1 From group_authentication where name='Administrador');

INSERT INTO group_authentication_form(
            id_group_authentication_form, name_class, delete, save, search, 
            update, group_authentication_id_group_authentication, ativo)
Select
	nextval('seq_group_authentication_form'),
	descricao,
	true,true,true,true,
	(Select id_group_authentication From group_authentication where name='Administrador'),
	true
From formularios
Where Not Exists(Select 1 From group_authentication_form Where name_class=descricao);

INSERT INTO usuario_grupo(
			id_usuario_grupo, 
			usuario_nome_usuario, 
			group_authentication_id_group_authentication, ativo)
Select
	nextval('seq_usuario_grupo'),
	'admin',
	(Select id_group_authentication From group_authentication where name='Administrador'),
	true
Where Not Exists (Select 1 
		  From usuario_grupo 
		  where usuario_nome_usuario='admin' 
		  and   group_authentication_id_group_authentication = (Select id_group_authentication From group_authentication where name='Administrador'));

INSERT INTO usuario_permissao(
            id_usuario_permissao, name_class, delete, save, search, 
            update, usuario_nome_usuario, ativo)
Select
	nextval('seq_usuario_permissao'),
	descricao,
	true,true,true,true,
	'admin',
	true
From formularios
Where Not Exists(Select 1 From usuario_permissao Where name_class=descricao and usuario_nome_usuario = 'admin');

Update usuario_permissao Set delete=true, save=true, update=true, search=true, ativo=true
Where (delete=false or update=false or save=false or search=false)
and   usuario_nome_usuario = 'admin';

DROP TABLE IF EXISTS formularios;
