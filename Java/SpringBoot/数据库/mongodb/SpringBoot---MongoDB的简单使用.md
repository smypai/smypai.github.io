## SpringBoot---MongoDB的简单使用
```java


一、引入依赖
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

二、配置文件
spring:
  application:
    name: xc‐service‐manage‐cms（本项目名）
  data:
    mongodb:
      uri: mongodb://root:123@localhost:27017（账号root, 密码123，端口27017）
      database: xc_cms（连接的数据库名称）

三、实体类
package com.xuecheng.framework.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @Author: mrt.
 * @Description:
 * @Date:Created in 2018/1/24 10:04.
 * @Modified By:
 */
//指定的集合名称
@Document(collection = "cms_page")
public class CmsPage {
    /**
     * 页面名称、别名、访问地址、类型（静态/动态）、页面模版、状态
     */
    //站点ID
    private String siteId;
    //页面ID
    @Id
    private String pageId;
    //页面名称
    private String pageName;
    //别名
    private String pageAliase;
    //访问地址
    private String pageWebPath;
    //参数
    private String pageParameter;
    //物理路径
    private String pagePhysicalPath;
    //类型（静态/动态）
    private String pageType;
    //页面模版
    private String pageTemplate;
    //页面静态化内容
    private String pageHtml;
    //状态
    private String pageStatus;
    //创建时间
    private Date pageCreateTime;
    //模版id
    private String templateId;
    //参数列表
    private List<CmsPageParam> pageParams;
    //模版文件Id
//    private String templateFileId;
    //静态文件Id
    private String htmlFileId;
    //数据Url
    private String dataUrl;


    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageAliase() {
        return pageAliase;
    }

    public void setPageAliase(String pageAliase) {
        this.pageAliase = pageAliase;
    }

    public String getPageWebPath() {
        return pageWebPath;
    }

    public void setPageWebPath(String pageWebPath) {
        this.pageWebPath = pageWebPath;
    }

    public String getPageParameter() {
        return pageParameter;
    }

    public void setPageParameter(String pageParameter) {
        this.pageParameter = pageParameter;
    }

    public String getPagePhysicalPath() {
        return pagePhysicalPath;
    }

    public void setPagePhysicalPath(String pagePhysicalPath) {
        this.pagePhysicalPath = pagePhysicalPath;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getPageTemplate() {
        return pageTemplate;
    }

    public void setPageTemplate(String pageTemplate) {
        this.pageTemplate = pageTemplate;
    }

    public String getPageHtml() {
        return pageHtml;
    }

    public void setPageHtml(String pageHtml) {
        this.pageHtml = pageHtml;
    }

    public String getPageStatus() {
        return pageStatus;
    }

    public void setPageStatus(String pageStatus) {
        this.pageStatus = pageStatus;
    }

    public Date getPageCreateTime() {
        return pageCreateTime;
    }

    public void setPageCreateTime(Date pageCreateTime) {
        this.pageCreateTime = pageCreateTime;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public List<CmsPageParam> getPageParams() {
        return pageParams;
    }

    public void setPageParams(List<CmsPageParam> pageParams) {
        this.pageParams = pageParams;
    }

    public String getHtmlFileId() {
        return htmlFileId;
    }

    public void setHtmlFileId(String htmlFileId) {
        this.htmlFileId = htmlFileId;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }
}

三、MongoRepository（和JPA一样，因为MongoDB和关系型数据库差别不大，也可以用@Query自定义查询语句）
package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Huang
 * @version 1.0
 * @date 2020/3/18 20:38
 */
@Repository
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {
	@Query(value = "{'pageId':?0, 'pageName':?1}")
    public CmsPage findByPageIdAndPageName(String pageId, String pageName);
	
	
	**
     * 查找除id以外的其他所有（参考）
     */
    @Query("{'id':{'$ne':?0}}")
    List<Module> findAllExceptId(String id);
}


四、单元测试
	 /**
     * 分页查询
     */
    @Test
    public void test01(){
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(1, 2, Sort.by(order));
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println("????" + all.getContent());
    }
	
	//添加
	@Test
	public void testInsert(){
	//定义实体类
	CmsPage cmsPage = new CmsPage();
	cmsPage.setSiteId("s01");
	cmsPage.setTemplateId("t01");
	cmsPage.setPageName("测试页面");
	cmsPage.setPageCreateTime(new Date());
	List<CmsPageParam> cmsPageParams = new ArrayList<>();
	CmsPageParam cmsPageParam = new CmsPageParam();
	cmsPageParam.setPageParamName("param1");
	cmsPageParam.setPageParamValue("value1");
	cmsPageParams.add(cmsPageParam);
	cmsPage.setPageParams(cmsPageParams);
	cmsPageRepository.save(cmsPage);
	System.out.println(cmsPage);
	}
	
	//删除
	@Test
	public void testDelete() {
	cmsPageRepository.deleteById("5b17a2c511fe5e0c409e5eb3");
	}
	
	//修改
	@Test
	public void testUpdate() {
	Optional<CmsPage> optional = cmsPageRepository.findOne("5b17a34211fe5e2ee8c116c9");
	if(optional.isPresent()){
	CmsPage cmsPage = optional.get();
	cmsPage.setPageName("测试页面01");
	cmsPageRepository.save(cmsPage);
	}
	
}

关于Optional：
Optional是jdk1.8引入的类型，Optional是一个容器对象，它包括了我们需要的对象，使用isPresent方法判断所包
含对象是否为空，isPresent方法返回false则表示Optional包含对象为空，否则可以使用get()取出对象进行操作。
Optional的优点是：
1、提醒你非空判断。
2、将对象非空检测标准化。


```