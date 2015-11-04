package model;

import java.io.IOException;

import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/** Classe que contém os metodos para autenticação em um switch.
 *
 * @author silas
 *
 */

public class Credenciais {
	UserTarget target;
	Snmp snmp;
	USM usm;
	UsmUser user;

	public Credenciais(){
		target = new UserTarget();
	}

	/** Metodo que configura o nivel de segurança das consultas.
	 *
	 * @param ip, ip de cada switch para consulta.
	 * @param user, Usuario snmp de cada switch
	 * @return UserTarget, uma instancia com o nivel segurança configurado.
	 */

	public UserTarget target(String ip, String user){
		target.setVersion(SnmpConstants.version3);
		target.setAddress(new UdpAddress(ip + "/161"));
		target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
		target.setSecurityName(new OctetString(user));
		target.setTimeout(3000);    //3s
		target.setRetries(0);
		return target;
	}

	/** Este metodo fornece as funções de enviar e receber SNMP PDUs.
	 *
	 * @return Snmp, metodo de envio e recebimento de PDUs SNMP.
	 */

	public Snmp snmp(){
		try {
			snmp = new Snmp(new DefaultUdpTransportMapping());
			usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
			SecurityModels.getInstance().addSecurityModel(usm);
			snmp.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return snmp;
	}

	/** Metodo para configuração das credenciais de consulta snmp ao ativo.
	 *
	 * @param usuario, usuario snmp com permissão de leitura
	 * @param senha, senha do usuario snmp com permissão de leitura
	 * @return UsmUser, representa um usuario SNMPv3.
	 */

	public UsmUser credenciais(String usuario, String senha){
		user = new UsmUser( new OctetString(usuario),
				AuthMD5.ID, new OctetString(senha),
				PrivDES.ID, new OctetString(senha));
		snmp.getUSM().addUser(new OctetString(usuario), user);
		return user;
	}




}
