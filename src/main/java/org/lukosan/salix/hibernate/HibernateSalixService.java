package org.lukosan.salix.hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.lukosan.salix.SalixConfiguration;
import org.lukosan.salix.SalixProperties;
import org.lukosan.salix.SalixResource;
import org.lukosan.salix.SalixService;
import org.lukosan.salix.SalixTemplate;
import org.lukosan.salix.SalixUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Transactional
public class HibernateSalixService implements SalixService {

	@Autowired @Qualifier("salixSessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	private SalixProperties properties;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override @SuppressWarnings("unchecked")
	public List<SalixUrl> activeUrls() {
		return getSession().createCriteria(HibernateSalixUrl.class)
				.add(Restrictions.lt("published", LocalDateTime.now()))
				.add(Restrictions.or(Restrictions.isNull("removed"), Restrictions.gt("removed", LocalDateTime.now())))
				.list();
	}
	
//	@SuppressWarnings("unchecked")
//	public List<SalixUrl> futureUrls() {
//		return getSession().createCriteria(HibernateSalixUrl.class)
//				.add(Restrictions.gt("published", LocalDateTime.now()))
//				.list();
//	}

	@Override
	public SalixUrl url(String requestUri) {
		return (HibernateSalixUrl) getSession().createCriteria(HibernateSalixUrl.class)
				.add(Restrictions.eq("url", requestUri)).uniqueResult();
	}

	@Override
	public SalixTemplate template(String name) {
		return (HibernateSalixTemplate) getSession().createCriteria(HibernateSalixTemplate.class)
				.add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override @SuppressWarnings("unchecked")
	public List<SalixUrl> allUrls() {
		return getSession().createCriteria(HibernateSalixUrl.class).list();
	}

	@Override
	public SalixUrl save(SalixUrl salixUrl) {
		return save(salixUrl.getScope(), salixUrl.getUrl(), salixUrl.getStatus().value(), salixUrl.getView(),
				salixUrl.getPublished(), salixUrl.getRemoved(), salixUrl.getMap());
	}

	@Override
	public SalixUrl save(String scope, String url, int status, String view, LocalDateTime published, LocalDateTime removed, Map<String, Object> map) {
		HibernateSalixUrl salixUrl = (HibernateSalixUrl) getSession().createCriteria(HibernateSalixUrl.class)
			.add(Restrictions.eq("url", url))
			.add(Restrictions.eq("scope", scope))
			.uniqueResult();
		if(null == salixUrl) {
			salixUrl = new HibernateSalixUrl();
			salixUrl.setUrl(url);
			salixUrl.setScope(scope);
		}
		salixUrl.setHttpStatus(status);
		salixUrl.setView(view);
		salixUrl.setPublished(published);
		salixUrl.setRemoved(removed);
		salixUrl.setMap(map);
		getSession().saveOrUpdate(salixUrl);
		return salixUrl;
	}

	@Override @SuppressWarnings("unchecked")
	public List<SalixTemplate> allTemplates() {
		return getSession().createCriteria(HibernateSalixTemplate.class).list();
	}

	@Override
	public SalixTemplate save(String scope, String resourceName, String source) {
		HibernateSalixTemplate salixTemplate = (HibernateSalixTemplate) getSession().createCriteria(HibernateSalixTemplate.class)
				.add(Restrictions.eq("name", resourceName))
				.add(Restrictions.eq("scope", scope))
				.uniqueResult();
		if(null == salixTemplate) {
			salixTemplate = new HibernateSalixTemplate();
			salixTemplate.setName(resourceName);
			salixTemplate.setScope(scope);
		}
		salixTemplate.setSource(source);
		getSession().saveOrUpdate(salixTemplate);
		return salixTemplate;
	}

	@Override
	public SalixTemplate template(String name, String scope) {
		return (SalixTemplate) getSession().createCriteria(HibernateSalixTemplate.class)
				.add(Restrictions.eq("name", name))
				.add(Restrictions.eq("scope", scope))
				.uniqueResult();
	}

	@Override
	public SalixUrl url(String url, String scope) {
		return (SalixUrl) getSession().createCriteria(HibernateSalixUrl.class)
				.add(Restrictions.eq("url", url))
				.add(Restrictions.eq("scope", scope))
				.uniqueResult();
	}

	@Override
	public SalixResource resource(String sourceId) {
		return (SalixResource) getSession().createCriteria(HibernateSalixResource.class)
				.add(Restrictions.eq("sourceId", sourceId)).uniqueResult();
	}

	@Override
	public SalixResource resource(String sourceId, String scope) {
		return (SalixResource) getSession().createCriteria(HibernateSalixResource.class)
				.add(Restrictions.eq("sourceId", sourceId))
				.add(Restrictions.eq("scope", scope))
				.uniqueResult();
	}

	@Override
	public SalixResource save(String scope, String sourceId, String sourceUri, Map<String, Object> map) {
		HibernateSalixResource salixResource = new HibernateSalixResource();
		salixResource.setScope(scope);
		salixResource.setSourceId(sourceId);
		salixResource.setSourceUri(sourceUri);
		salixResource.setMap(map);
		getSession().saveOrUpdate(salixResource);
		return salixResource;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> scopes() {
		Set<String> scopes = (Set<String>) getSession().createCriteria(HibernateSalixConfiguration.class).setProjection(Projections.distinct(
					Projections.projectionList().add(Projections.property("scope"))
				)).list().stream().map(s -> s.toString()).collect(Collectors.toSet());
		scopes.addAll(properties.getScopes());
		return scopes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalixConfiguration> allConfigurations() {
		return getSession().createCriteria(HibernateSalixConfiguration.class).list();
	}

	@Override
	public SalixConfiguration configuration(String scope, String target) {
		return (SalixConfiguration) getSession().createCriteria(HibernateSalixConfiguration.class)
				.add(Restrictions.eq("target", target))
				.add(Restrictions.eq("scope", scope))
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalixConfiguration> configurationsIn(String scope) {
		return getSession().createCriteria(HibernateSalixConfiguration.class)
				.add(Restrictions.eq("scope", scope))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalixConfiguration> configurationsFor(String target) {
		return getSession().createCriteria(HibernateSalixConfiguration.class)
				.add(Restrictions.eq("target", target))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalixResource> allResources() {
		return getSession().createCriteria(HibernateSalixResource.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalixTemplate> templatesIn(String scope) {
		return getSession().createCriteria(HibernateSalixTemplate.class)
				.add(Restrictions.eq("scope", scope))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalixResource> resourcesIn(String scope) {
		return getSession().createCriteria(HibernateSalixResource.class)
				.add(Restrictions.eq("scope", scope))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalixUrl> urlsIn(String scope) {
		return getSession().createCriteria(HibernateSalixUrl.class)
				.add(Restrictions.eq("scope", scope))
				.list();
	}

	@Override
	public SalixConfiguration save(String scope, String target, Map<String, Object> map) {
		HibernateSalixConfiguration salixConfiguration = (HibernateSalixConfiguration) getSession().createCriteria(HibernateSalixConfiguration.class)
				.add(Restrictions.eq("target", target))
				.add(Restrictions.eq("scope", scope))
				.uniqueResult();
		if(null == salixConfiguration) {
			salixConfiguration = new HibernateSalixConfiguration();
			salixConfiguration.setTarget(target);
			salixConfiguration.setScope(scope);
		}
		salixConfiguration.setMap(map);
		getSession().saveOrUpdate(salixConfiguration);
		return salixConfiguration;		
	}

}