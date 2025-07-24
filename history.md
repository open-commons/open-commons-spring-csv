[2025/07/24]
- Dependencies
  + Add
    + jakarta.validation:jakarata.validation-api:${managed-version}
    + com.google.code.findbugs:jsr305:${managed-version}

[2025/02/21]
Apply 'Maven Central Deployment'

- Update
  + <deploymentManagement>
    + Release: Maven Central (https://central.sonatype.com)
  + 'open.commons' dependencies 
    + groupId: io.github.open-commons
- Add
  + <build>
    + org.sonatype.central:central-publishing-maven-plugin
    + org.apache.maven.plugins:maven-gpg-plugin

[2025/02/17]
- Snapshot: 0.3.0-SNAPSHOT
- Dependencies:
  + open-commons-spring-web-dependencies: 0.8.0-SNAPSHOT

[2025/02/17]
- Release: 0.2.0


[2024/11/06]
- Modify
  + spring-data-commons: v2.x.x 최대값으로 고정
  
[2024/10/31]
- ETC
  + Maven Repository 주소 변경 (http -> https)
  
[2022/11/17]
- Dependencies
	+ spring-core.version: 5.3.23 고정

[2022/04/07]
- Release: 0.2.0-SNAPSHOT
- Tag: 0.1.0
- Dependencies:
  + open.commons.core: 2.0.0-SNAPSHOT

[2022/04/07]
- Release: 0.1.0

[2021/12/21]
- Add
  + open.commons.csv.CsvHeader
    + CsvHeader(@JsonProperty("name") String, @JsonProperty("dataType") ColumnDataType): 명시적으로 JsonProperty 추가

[2021/12/15]
- Modify
  + open.commons.csv.AbstractCsvFileLoad: REST API RequestEntity로 사용하기 위한 #headers 'Setter' 메소드 설정 추가
    - setHeaders(CsvHeader[]): @JsonSetter 추가. 
  + open.commons.csv.CsvHeader: REST API RequestEntity로 사용하기 위한 기본 생성자 설정 추가
    - CsvHeader(String, ColumnDataType)에 @JsonCreator 추가. 

[2021/12/08]
- Initialized