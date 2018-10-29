package br.com.jhisolution.apex.web.rest;

import br.com.jhisolution.apex.ApexApp;

import br.com.jhisolution.apex.domain.Cadastro;
import br.com.jhisolution.apex.repository.CadastroRepository;
import br.com.jhisolution.apex.service.CadastroService;
import br.com.jhisolution.apex.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static br.com.jhisolution.apex.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CadastroResource REST controller.
 *
 * @see CadastroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApexApp.class)
public class CadastroResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_GIF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GIF = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_GIF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GIF_CONTENT_TYPE = "image/png";

    @Autowired
    private CadastroRepository cadastroRepository;

    

    @Autowired
    private CadastroService cadastroService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCadastroMockMvc;

    private Cadastro cadastro;

    @Before
    public void setup() {
        /*MockitoAnnotations.initMocks(this);
        final CadastroResource cadastroResource = new CadastroResource(cadastroService);
        this.restCadastroMockMvc = MockMvcBuilders.standaloneSetup(cadastroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();*/
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cadastro createEntity(EntityManager em) {
        Cadastro cadastro = new Cadastro()
            .nome(DEFAULT_NOME)
            .email(DEFAULT_EMAIL)
            .gif(DEFAULT_GIF)
            .gifContentType(DEFAULT_GIF_CONTENT_TYPE);
        return cadastro;
    }

    @Before
    public void initTest() {
        cadastro = createEntity(em);
    }

    @Test
    @Transactional
    public void createCadastro() throws Exception {
        int databaseSizeBeforeCreate = cadastroRepository.findAll().size();

        // Create the Cadastro
        restCadastroMockMvc.perform(post("/api/cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cadastro)))
            .andExpect(status().isCreated());

        // Validate the Cadastro in the database
        List<Cadastro> cadastroList = cadastroRepository.findAll();
        assertThat(cadastroList).hasSize(databaseSizeBeforeCreate + 1);
        Cadastro testCadastro = cadastroList.get(cadastroList.size() - 1);
        assertThat(testCadastro.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCadastro.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCadastro.getGif()).isEqualTo(DEFAULT_GIF);
        assertThat(testCadastro.getGifContentType()).isEqualTo(DEFAULT_GIF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCadastroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cadastroRepository.findAll().size();

        // Create the Cadastro with an existing ID
        cadastro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCadastroMockMvc.perform(post("/api/cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cadastro)))
            .andExpect(status().isBadRequest());

        // Validate the Cadastro in the database
        List<Cadastro> cadastroList = cadastroRepository.findAll();
        assertThat(cadastroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadastroRepository.findAll().size();
        // set the field null
        cadastro.setNome(null);

        // Create the Cadastro, which fails.

        restCadastroMockMvc.perform(post("/api/cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cadastro)))
            .andExpect(status().isBadRequest());

        List<Cadastro> cadastroList = cadastroRepository.findAll();
        assertThat(cadastroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadastroRepository.findAll().size();
        // set the field null
        cadastro.setEmail(null);

        // Create the Cadastro, which fails.

        restCadastroMockMvc.perform(post("/api/cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cadastro)))
            .andExpect(status().isBadRequest());

        List<Cadastro> cadastroList = cadastroRepository.findAll();
        assertThat(cadastroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCadastros() throws Exception {
        // Initialize the database
        cadastroRepository.saveAndFlush(cadastro);

        // Get all the cadastroList
        restCadastroMockMvc.perform(get("/api/cadastros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cadastro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].gifContentType").value(hasItem(DEFAULT_GIF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].gif").value(hasItem(Base64Utils.encodeToString(DEFAULT_GIF))));
    }
    

    @Test
    @Transactional
    public void getCadastro() throws Exception {
        // Initialize the database
        cadastroRepository.saveAndFlush(cadastro);

        // Get the cadastro
        restCadastroMockMvc.perform(get("/api/cadastros/{id}", cadastro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cadastro.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.gifContentType").value(DEFAULT_GIF_CONTENT_TYPE))
            .andExpect(jsonPath("$.gif").value(Base64Utils.encodeToString(DEFAULT_GIF)));
    }
    @Test
    @Transactional
    public void getNonExistingCadastro() throws Exception {
        // Get the cadastro
        restCadastroMockMvc.perform(get("/api/cadastros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCadastro() throws Exception {
        // Initialize the database
        cadastroService.save(cadastro);

        int databaseSizeBeforeUpdate = cadastroRepository.findAll().size();

        // Update the cadastro
        Cadastro updatedCadastro = cadastroRepository.findById(cadastro.getId()).get();
        // Disconnect from session so that the updates on updatedCadastro are not directly saved in db
        em.detach(updatedCadastro);
        updatedCadastro
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .gif(UPDATED_GIF)
            .gifContentType(UPDATED_GIF_CONTENT_TYPE);

        restCadastroMockMvc.perform(put("/api/cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCadastro)))
            .andExpect(status().isOk());

        // Validate the Cadastro in the database
        List<Cadastro> cadastroList = cadastroRepository.findAll();
        assertThat(cadastroList).hasSize(databaseSizeBeforeUpdate);
        Cadastro testCadastro = cadastroList.get(cadastroList.size() - 1);
        assertThat(testCadastro.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCadastro.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCadastro.getGif()).isEqualTo(UPDATED_GIF);
        assertThat(testCadastro.getGifContentType()).isEqualTo(UPDATED_GIF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCadastro() throws Exception {
        int databaseSizeBeforeUpdate = cadastroRepository.findAll().size();

        // Create the Cadastro

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCadastroMockMvc.perform(put("/api/cadastros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cadastro)))
            .andExpect(status().isBadRequest());

        // Validate the Cadastro in the database
        List<Cadastro> cadastroList = cadastroRepository.findAll();
        assertThat(cadastroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCadastro() throws Exception {
        // Initialize the database
        cadastroService.save(cadastro);

        int databaseSizeBeforeDelete = cadastroRepository.findAll().size();

        // Get the cadastro
        restCadastroMockMvc.perform(delete("/api/cadastros/{id}", cadastro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cadastro> cadastroList = cadastroRepository.findAll();
        assertThat(cadastroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cadastro.class);
        Cadastro cadastro1 = new Cadastro();
        cadastro1.setId(1L);
        Cadastro cadastro2 = new Cadastro();
        cadastro2.setId(cadastro1.getId());
        assertThat(cadastro1).isEqualTo(cadastro2);
        cadastro2.setId(2L);
        assertThat(cadastro1).isNotEqualTo(cadastro2);
        cadastro1.setId(null);
        assertThat(cadastro1).isNotEqualTo(cadastro2);
    }
}
