##maven插件－自动化部署工具
**使用方法：**

	<build>
		<plugins>
			<plugin>
				<groupId>com.asd.template</groupId>
				<artifactId>template-plugin</artifactId>
				<version>0.0.1-SNAPSHOT</version>
				<configuration>
					<deployEnabled>true</deployEnabled>
					<deployMode>redeploy</deployMode>
					<host>192.168.40.43</host>
					<user>root</user>
					<password>admin</password>
					<catalinaHome>/usr/local/apache-tomcat-8.0.29</catalinaHome>
				</configuration>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>autoDeploy</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
