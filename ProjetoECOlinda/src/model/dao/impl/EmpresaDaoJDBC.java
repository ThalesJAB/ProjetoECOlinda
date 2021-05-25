package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import db.DbException;
import model.dao.EmpresaDao;
import model.entities.Empresa;
import model.entities.Endereco;
import model.entities.Residuo;
import model.entities.Telefone;

public class EmpresaDaoJDBC implements EmpresaDao {

	private Connection connection;

	public EmpresaDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void cadastrar(Empresa empresa) {

	}

	@Override
	public void alterar(Empresa empresa) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletar(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Empresa procurar(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Empresa> listarEmpresas() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = connection.prepareStatement("SELECT " + "	EMPRESA.*, " + "	TELEFONE.id_telefone, "
					+ "	TELEFONE.telefone, " + "	TELEFONE.empresa_id_empresa, " + "	ENDERECO.id_endereco, "
					+ "	ENDERECO.cep, " + "	ENDERECO.logradouro, " + "	ENDERECO.numero, " + "	ENDERECO.complemento, "
					+ "	ENDERECO.bairro, " + "	ENDERECO.cidade, " + "	ENDERECO.estado, "
					+ "	ENDERECO.empresa_id_empresa, " + "	RESIDUO.id_residuo, " + "	RESIDUO.tipo_residuo, "
					+ "	RESIDUO.descricao_residuo " + "FROM EMPRESA " + "INNER JOIN TELEFONE "
					+ "ON EMPRESA.id_empresa = TELEFONE.empresa_id_empresa " + "INNER JOIN ENDERECO "
					+ "ON EMPRESA.id_empresa = ENDERECO.empresa_id_empresa " + "INNER JOIN RESIDUO_EMPRESA "
					+ "ON EMPRESA.id_empresa = RESIDUO_EMPRESA.empresa_id_empresa " + "INNER JOIN RESIDUO "
					+ "ON RESIDUO_EMPRESA.residuo_id_residuo = RESIDUO.id_residuo "
					+ "WHERE EMPRESA.status = true AND TELEFONE.status = true AND ENDERECO.status = true AND RESIDUO_EMPRESA.status = true;");

			rs = st.executeQuery();
			List<Empresa> empresas = new ArrayList<>();
			
			Map<Integer, Telefone> telefoneMap = new HashMap<>();
			Map<Integer, Residuo> residuoMap = new HashMap<>();
			Map<Integer, Endereco> enderecoMap = new HashMap<>();
			Map<Integer, Empresa> empresaMap = new HashMap<>();

			while (rs.next()) {
				Telefone telefone = telefoneMap.get(rs.getInt("id_telefone"));
				Residuo residuo = residuoMap.get(rs.getInt("id_residuo"));
				Endereco endereco = enderecoMap.get(rs.getInt("id_endereco"));
				Empresa empresa = empresaMap.get(rs.getInt("id_empresa"));
				
				if(Objects.isNull(telefone)) {
					telefone = instanciarTelefone(rs);
					telefoneMap.put(rs.getInt("id_telefone"), telefone);
					
				}
				
				if(Objects.isNull(residuo)) {
					residuo = instanciarResiduo(rs);
					residuoMap.put(rs.getInt("id_residuo"), residuo);
				}
				
				if(Objects.isNull(endereco)) {
					endereco = instanciarEndereco(rs);
					enderecoMap.put(rs.getInt("id_endereco"), endereco);
				}
				
				if(Objects.isNull(empresa)) {
					empresa = instanciarEmpresa(rs);
					empresaMap.put(rs.getInt("id_empresa"), empresa);
				}
				
				if(!empresa.getTelefones().contains(telefone)) {
					empresa.addTelefone(telefone);
					
				}
				
				if(!empresa.getEnderecos().contains(endereco)) {
					empresa.addEndereco(endereco);
				}
				
				if(!empresa.getResiduos().contains(residuo)) {
					empresa.addResiduo(residuo);
				}
				
				if(!empresas.contains(empresa)){
					empresas.add(empresa);
				}	
				
			}
			
			return empresas;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	

	}
	

	public Telefone instanciarTelefone(ResultSet rs) throws SQLException {
		Telefone telefone = new Telefone();
		telefone.setId(rs.getInt("id_telefone"));
		telefone.setNumTelefone(rs.getString("telefone"));
		telefone.setIdEmpresa(rs.getInt("empresa_id_empresa"));
		telefone.setStatus(true);
		return telefone;
	}
	
	public Residuo instanciarResiduo(ResultSet rs) throws SQLException {
		Residuo residuo = new Residuo();
		residuo.setId(rs.getInt("id_residuo"));
		residuo.setTipoResiduo(rs.getString("tipo_residuo"));
		residuo.setDescricaoResiduo(rs.getString("descricao_residuo"));
		residuo.setStatus(true);

		return residuo;

	}
	
	public Endereco instanciarEndereco(ResultSet rs) throws SQLException {
		Endereco endereco = new Endereco();
		endereco.setId(rs.getInt("id_endereco"));
		endereco.setCep(rs.getString("cep"));
		endereco.setLogradouro(rs.getString("logradouro"));
		endereco.setNumero(rs.getInt("numero"));
		endereco.setComplemento(rs.getString("complemento"));
		endereco.setBairro(rs.getString("bairro"));
		endereco.setCidade(rs.getString("cidade"));
		endereco.setEstado(rs.getString("estado"));
		endereco.setIdEmpresa(rs.getInt("empresa_id_empresa"));
		endereco.setStatus(true);

		return endereco;

	}
	
	public Empresa instanciarEmpresa(ResultSet rs) throws SQLException {
		Empresa empresa = new Empresa();
		empresa.setId(rs.getInt("id_empresa"));
		empresa.setNome(rs.getString("nome_empresa"));
		empresa.setEmail(rs.getString("email_empresa"));
		empresa.setLogin(rs.getString("login_empresa"));
		empresa.setSenha(rs.getString("senha_empresa"));
		empresa.setStatus(rs.getBoolean("status"));

		return empresa;
		
	}
	
	
}
