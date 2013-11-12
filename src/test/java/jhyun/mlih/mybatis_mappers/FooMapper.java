package jhyun.mlih.mybatis_mappers;

import java.util.List;

import jhyun.mlih.entities.Foo;

public interface FooMapper {

	public int count();

	public List<Foo> list();

	public Foo selectOne(int id);

	public List<Foo> selectMany(List<Integer> ids);

	public int newFooId();

	public void insert(Foo foo);

	public void delete(int id);

}
