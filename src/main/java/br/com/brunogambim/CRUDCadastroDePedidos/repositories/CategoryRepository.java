package br.com.brunogambim.CRUDCadastroDePedidos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.brunogambim.CRUDCadastroDePedidos.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
