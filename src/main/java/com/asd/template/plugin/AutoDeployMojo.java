/**
 * 
 */
package com.asd.template.plugin;

import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.asd.template.plugin.util.AutoDeploy;

/**
 * 
 * 自动化部署插件
 * 
 * @author <a href="mailto:wengyingjian@foxmail.com">翁英健</a>
 * @version 1.1 2015年12月7日
 * @since 1.1
 * 
 * @goal autoDeploy
 * @phase package
 * @requiresProject true 该命令只能在Maven项目下执行，因为他依赖maven项目
 */
public class AutoDeployMojo extends AbstractMojo {
	// -----------以下为maven内置属性------------
	/**
	 * 构建，输出目录：默认为 target/
	 * 
	 * @parameter expression="${project.build.directory}"
	 * @required
	 * @readonly
	 */
	private String buildDir;

	/**
	 * 项目打包后的文件名
	 * 
	 * @parameter expression="${project.build.finalName}"
	 * @required
	 * @readonly
	 */
	private String fileName;
	/**
	 * 项目打包的类型，默认为jar，此处需要war
	 * 
	 * @parameter expression="${project.packaging}"
	 * @required
	 * @readonly
	 */
	private String packaging;

	// ---------------以下为需要配置的属性--------------------
	/**
	 * 是否开起自动化部署，在配置文件或者运行时传入该参数
	 * 
	 * @parameter
	 * @required
	 */
	private boolean autoDeploy = true;
	/**
	 * 目标主机地址
	 * 
	 * @parameter
	 * @required
	 */
	private String host;
	/**
	 * 目标主机用户名
	 * 
	 * @parameter
	 * @required
	 */
	private String user;
	/**
	 * 目标主机密码
	 * 
	 * @parameter
	 * @required
	 */
	private String password;
	/**
	 * 目标主机tomcat home目录
	 * 
	 * @parameter
	 * @required
	 */
	private String catalinaHome;
	/**
	 * 监控日志的文件
	 * 
	 * @parameter
	 */
	private String loggerFile;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {

		if (autoDeploy) {
			if ("war".equals(packaging)) {
				String sourceWarFile = new StringBuilder(buildDir).append("/").append(fileName).append(".")
						.append(packaging).toString();
				System.out.println("target package file is :" + sourceWarFile);
				AutoDeploy autoDeploy = null;
				if (loggerFile == null) {
					autoDeploy = new AutoDeploy(host, user, password, sourceWarFile, catalinaHome);
				} else {
					autoDeploy = new AutoDeploy(host, user, password, sourceWarFile, catalinaHome, loggerFile);
				}
				try {
					autoDeploy.deploy();
				} catch (IOException e) {
					e.printStackTrace();
					throw new MojoExecutionException(e.getMessage());
				}
			} else {
				System.out.println("only war packaging project supports autoDeploy!");
			}
		}
	}

}
