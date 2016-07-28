package com.luxoft.wheretogo.repositories;

import com.luxoft.wheretogo.models.Event;
import com.luxoft.wheretogo.models.Group;
import com.luxoft.wheretogo.models.User;
import com.luxoft.wheretogo.utils.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class EventsRepositoryImpl extends AbstractRepository<Event> implements EventsRepository {

	private final static Logger LOGGER = Logger.getLogger(EventsRepositoryImpl.class);

	public EventsRepositoryImpl() {
		super(Event.class);
	}

	public void add(Event event) {
		super.add(event);
	}

	public void merge(Event event) {
		super.merge(event);
	}

	@Override
	public List<Event> findAll() {
		return super.findAll("id");
	}

	@Override
	public List<Event> findByPeriod(LocalDateTime from, LocalDateTime to) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.ge("startDateTime", DateUtils.covertToDate(from)));
		criteria.add(Restrictions.lt("startDateTime", DateUtils.covertToDate(to)));

		List<Event> result = criteria.list();

		LOGGER.debug(String.format("found %d events", result.size()));
		return result;
	}

	@Override
	public Event findById(long eventId) {
		return super.findByProperty("id", eventId);
	}

	@Override
	public Event findByName(String eventName) {
		return super.findByProperty("name", eventName);
	}

	@Override
	public List<Event> findByOwner(User owner) {
		return super.findListByProperty("owner", owner);
	}

	@Override
	public List<Event> findByTargetGroup(Group targetGroup) {
		return super.findListByProperty("targetGroup", targetGroup);
	}

}
