package br.ufrn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.conexao.ConnectionFactory;
import br.ufrn.model.Andar;
import br.ufrn.model.Municipio;
import br.ufrn.model.Pavimento;
import br.ufrn.model.Predio;
import br.ufrn.model.Unidade;
import br.ufrn.model.Vlan;

public class DAOVlan {
	private Connection conexao;

	public DAOVlan(){
		this.conexao = new ConnectionFactory().getConnection();
	}

	public void adicionarVlan(Vlan vlan){
		//Fazendo uma string com o comando para inserir os dados na tabela interface
		String sql = "INSERT INTO vlan (nomevlan, numerovlan, faixaIP, mascara, gateway, dns, dhcp) VALUES (?,?,?,?,?,?,?)";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, vlan.getNomevlan());
			ps.setString(2, vlan.getNumerovlan());
			ps.setString(3, vlan.getFaixaIP());
			ps.setString(4, vlan.getMascara());
			ps.setString(5, vlan.getGateway());
			ps.setString(6, vlan.getDns());
			ps.setString(7, vlan.getDhcp());
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}

	public List<Vlan> listarVlans(){
		List<Vlan> vlans = new ArrayList<Vlan>();
		ResultSet rs;
		String sql = "select id_vlan, nomevlan, numerovlan, faixaIP, mascara, gateway, dns, dhcp from vlan;";
		Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				
				Vlan vlan = new Vlan();
				int id_vlan = Integer.parseInt(rs.getString("id_vlan")); 
				vlan.setId(id_vlan);
				vlan.setNomevlan(rs.getString("nomevlan"));
				vlan.setNumerovlan(rs.getString("numerovlan"));
				vlan.setFaixaIP(rs.getString("faixaIP"));
				vlan.setMascara(rs.getString("mascara"));
				vlan.setGateway(rs.getString("gateway"));
				vlan.setDns(rs.getString("dns"));
				vlan.setDhcp(rs.getString("dhcp"));
				
				vlans.add(vlan);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vlans;
	}
	
	
	public Vlan listarVlan(String codigo){
		Vlan vlan = new Vlan();
		ResultSet rs;
		String sql = "select id_vlan, nomevlan, numerovlan, faixaIP, mascara, gateway, dns, dhcp from vlan"
				+ " where id_vlan = '" + codigo + "';";
		Statement st;
		try {
			st = conexao.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				int id_vlan = Integer.parseInt(rs.getString("id_vlan")); 
				vlan.setId(id_vlan);
				vlan.setNomevlan(rs.getString("nomevlan"));
				vlan.setNumerovlan(rs.getString("numerovlan"));
				vlan.setFaixaIP(rs.getString("faixaIP"));
				vlan.setMascara(rs.getString("mascara"));
				vlan.setGateway(rs.getString("gateway"));
				vlan.setDns(rs.getString("dns"));
				vlan.setDhcp(rs.getString("dhcp"));
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vlan;
	}

	public boolean vlanValida(String vlan) {
		boolean autenticar = false;
		String sql = "select * from vlan where " + "numerovlan='"+vlan+"';";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rm = ps.executeQuery();
			if(rm.next()){
				autenticar = true;
			}
			rm.close();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
		return autenticar;
	}
	
	public void editarVlan(Vlan vlan){
		String sql = "UPDATE vlan set "
				+ " nomevlan = '"+ vlan.getNomevlan()+ "', "
				+ " numerovlan = '"+ vlan.getNumerovlan()+ "', "
				+ " faixaIP = '"+ vlan.getFaixaIP()+ "', "
				+ " mascara = '"+ vlan.getMascara()+ "', "
				+ " gateway = '"+ vlan.getGateway()+ "', "
				+ " dns = '"+ vlan.getDns()+"', "
				+ " dhcp = '"+ vlan.getDhcp()+ "'"
				+ " where id_vlan = '" + vlan.getId()+ "';";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}
	
	public void deletarVlan(int id){
		String sql = "DELETE FROM vlan where id_vlan = '" + id+ "';";
		try{
			PreparedStatement ps = conexao.prepareStatement(sql);
			//ps.setArray(i, inte.get(i));
			ps.execute();
			ps.close();
		}catch(SQLException erro){
			throw new RuntimeException(erro);
		}
	}
}