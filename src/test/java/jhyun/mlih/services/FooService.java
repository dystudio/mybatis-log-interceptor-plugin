package jhyun.mlih.services;

import java.util.List;

import jhyun.mlih.entities.Foo;
import jhyun.mlih.mybatis_mappers.FooMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FooService {

	@Autowired
	private FooMapper fooMapper;

	public int count() {
		return fooMapper.count();
	}

	public List<Foo> list() {
		return fooMapper.list();
	}

	public Foo selectOne(int id) {
		return fooMapper.selectOne(id);
	}

	public List<Foo> selectMany(List<Integer> ids) {
		return fooMapper.selectMany(ids);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public int newFooId() {
		return fooMapper.newFooId();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public void insert(Foo foo) {
		fooMapper.insert(foo);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Throwable.class })
	public void delete(int id) {
		fooMapper.delete(id);
	}

}
