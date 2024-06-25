package com.example.ITired;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ITiredApplicationTests {

	@Test
	void contextLoads() {
	}

}

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        String viewName = userController.login();
        assertEquals("login", viewName);
    }

    @Test
    void testRegistration() {
        String viewName = userController.registration(model);
        assertEquals("registration", viewName);
        verify(model, times(1)).addAttribute(eq("name"), any(User.class));
    }

    @Test
    void testLogout() {
        String viewName = userController.logout();
        assertEquals("redirect:/index", viewName);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        String viewName = userController.createUser(user, model);
        assertEquals("redirect:/login", viewName);
        verify(userService, times(1)).createUser(user);
    }

    @Test
    void testBasketPage() {
        String viewName = userController.basketPage();
        assertEquals("basket", viewName);
    }

    @Test
    void testServicePage() {
        String viewName = userController.servicePage();
        assertEquals("service", viewName);
    }

    @Test
    void testContactsPage() {
        String viewName = userController.contactsPage();
        assertEquals("contacts", viewName);
    }

    @Test
    void testUserProfile() {
        User user = new User();
        when(userService.getCurrentUser()).thenReturn(user);
        String viewName = userController.userProfile(model, principal);
        assertEquals("profile", viewName);
        verify(model, times(1)).addAttribute("user", user);
    }

    @Test
    void testEditProfile() {
        User user = new User();
        when(userService.getCurrentUser()).thenReturn(user);
        String viewName = userController.editProfile(user, "newPassword");
        assertEquals("redirect:/profile", viewName);
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    void testUploadPhoto() {
        User user = new User();
        when(userService.getCurrentUser()).thenReturn(user);
        when(file.getBytes()).thenReturn(new byte[0]);
        String viewName = userController.uploadPhoto(file);
        assertEquals("redirect:/profile", viewName);
        verify(userService, times(1)).saveUser(user);
    }

    // Uncomment this test case if you uncomment the listUsers method in your controller
    // @Test
    // void testListUsers() {
    //     List<User> users = new ArrayList<>();
    //     users.add(new User());
    //     when(userService.findAllUsers()).thenReturn(users);
    //     String viewName = userController.listUsers(model);
    //     assertEquals("other-clinics", viewName);
    //     verify(model, times(1)).addAttribute(eq("users"), anyList());
    // }
}



class ServiceControllerTest {

    @Mock
    private ServiceSer serviceSer;

    @Mock
    private UserService userService;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ServiceController serviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testServicePage() {
        Long serviceId = 1L;
        Service service = new Service();
        when(serviceSer.getServiceById(serviceId)).thenReturn(service);

        String viewName = serviceController.servicePage(serviceId, model);

        assertEquals("service", viewName);
        verify(serviceSer, times(1)).getServiceById(serviceId);
        verify(model, times(1)).addAttribute("service", service);
    }

    @Test
    void testMakeAppointment_UserNotLoggedIn() {
        Long serviceId = 1L;
        String date = "2023-06-25";
        String time = "10:00";

        SecurityContextHolder.getContext().setAuthentication(null);

        String viewName = serviceController.makeAppointment(serviceId, date, time, session, model);

        assertEquals("redirect:/login", viewName);
    }

    @Test
    void testMakeAppointment_UserLoggedIn() {
        Long serviceId = 1L;
        String date = "2023-06-25";
        String time = "10:00";
        User user = new User();
        user.setId(1L);

        when(authentication.getName()).thenReturn("username");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.findByUsername("username")).thenReturn(user);
        when(appointmentService.userHasAppointmentAtTime(anyLong(), any(LocalDateTime.class))).thenReturn(false);
        when(serviceSer.getServiceById(serviceId)).thenReturn(new Service());
        when(session.getAttribute("cart")).thenReturn(new ArrayList<Appointment>());

        String viewName = serviceController.makeAppointment(serviceId, date, time, session, model);

        assertEquals("redirect:/cart", viewName);
        verify(serviceSer, times(1)).saveAppointment(any(Appointment.class));
    }

    @Test
    void testMakeAppointment_UserAlreadyHasAppointment() {
        Long serviceId = 1L;
        String date = "2023-06-25";
        String time = "10:00";
        User user = new User();
        user.setId(1L);

        when(authentication.getName()).thenReturn("username");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.findByUsername("username")).thenReturn(user);
        when(appointmentService.userHasAppointmentAtTime(anyLong(), any(LocalDateTime.class))).thenReturn(true);

        String viewName = serviceController.makeAppointment(serviceId, date, time, session, model);

        assertEquals("redirect:/", viewName);
        verify(model, times(1)).addAttribute("error", "Вы уже записаны на это время.");
    }

    @Test
    void testCart() {
        User user = new User();
        user.setId(1L);
        List<Appointment> appointments = new ArrayList<>();

        when(authentication.getName()).thenReturn("username");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userService.findByUsername("username")).thenReturn(user);
        when(serviceSer.getUserAppointments(user.getId())).thenReturn(appointments);

        String viewName = serviceController.cart(model);

        assertEquals("cart", viewName);
        verify(model, times(1)).addAttribute("appointments", appointments);
    }

    @Test
    void testCancelAppointment() {
        Long appointmentId = 1L;

        String viewName = serviceController.cancelAppointment(appointmentId);

        assertEquals("redirect:/user/appointments", viewName);
        verify(appointmentService, times(1)).cancelAppointment(appointmentId);
    }
}

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testFindById() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);
        assertNotNull(foundUser);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        User user = new User();
        user.setUsername("existingUser");

        when(userRepository.findByUsername("existingUser")).thenReturn(new User());

        boolean result = userService.createUser(user);
        assertFalse(result);
    }

    @Test
    void testCreateUser_NewUser() {
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("password");

        when(userRepository.findByUsername("newUser")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        boolean result = userService.createUser(user);
        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        when(userRepository.findByUsername("username")).thenReturn(user);

        User foundUser = userService.findByUsername("username");
        assertNotNull(foundUser);
        verify(userRepository, times(1)).findByUsername("username");
    }

    @Test
    void testList() {
        List<User> users = Collections.singletonList(new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.list();
        assertEquals(1, result.size());
    }

    @Test
    void testBanUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setActive(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.banUser(1L);
        assertFalse(user.isActive());

        userService.banUser(1L);
        assertTrue(user.isActive());

        verify(userRepository, times(2)).save(user);
    }

    @Test
    void testGetCurrentUser() {
        User user = new User();
        user.setUsername("username");

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return "username";
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        });
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userRepository.findByUsername("username")).thenReturn(user);

        User currentUser = userService.getCurrentUser();
        assertNotNull(currentUser);
        assertEquals("username", currentUser.getUsername());
    }

    @Test
    void testSaveUser() {
        User user = new User();
        userService.saveUser(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testEncodePassword() {
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        String result = userService.encodePassword(rawPassword);
        assertEquals(encodedPassword, result);
    }

    @Test
    void testFindAllUsers() {
        List<User> users = Collections.singletonList(new User());
        when(userRepository.findByRoles(Role.ROLE_USER)).thenReturn(users);

        List<User> result = userService.findAllUsers();
        assertEquals(1, result.size());
    }
}

class ServiceSerTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private OtherClinicRepository otherClinicRepository;

    @InjectMocks
    private ServiceSer serviceSer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListServices() {
        Service service = new Service();
        List<Service> services = Collections.singletonList(service);
        when(serviceRepository.findAll()).thenReturn(services);

        List<Service> result = serviceSer.listServices(null);
        assertEquals(1, result.size());
        verify(serviceRepository, times(1)).findAll();
    }

    @Test
    void testSaveService() {
        Service service = new Service();
        serviceSer.saveService(service);
        verify(serviceRepository, times(1)).save(service);
    }

    @Test
    void testDeleteService() {
        serviceSer.deleteService(1L);
        verify(serviceRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetServiceById() {
        Service service = new Service();
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        Service result = serviceSer.getServiceById(1L);
        assertNotNull(result);
        verify(serviceRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveAppointment() {
        Appointment appointment = new Appointment();
        serviceSer.saveAppointment(appointment);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void testGetUserAppointments() {
        Appointment appointment = new Appointment();
        List<Appointment> appointments = Collections.singletonList(appointment);
        when(appointmentRepository.findByUserId(1L)).thenReturn(appointments);

        List<Appointment> result = serviceSer.getUserAppointments(1L);
        assertEquals(1, result.size());
        verify(appointmentRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testGetAllClinics() {
        OtherClinic clinic = new OtherClinic();
        List<OtherClinic> clinics = Collections.singletonList(clinic);
        when(otherClinicRepository.findAll()).thenReturn(clinics);

        List<OtherClinic> result = serviceSer.getAllClinics();
        assertEquals(1, result.size());
        verify(otherClinicRepository, times(1)).findAll();
    }

    @Test
    void testGetClinicById() {
        OtherClinic clinic = new OtherClinic();
        when(otherClinicRepository.findById(1L)).thenReturn(Optional.of(clinic));

        OtherClinic result = serviceSer.getClinicById(1L);
        assertNotNull(result);
        verify(otherClinicRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveClinic() {
        OtherClinic clinic = new OtherClinic();
        serviceSer.saveClinic(clinic);
        verify(otherClinicRepository, times(1)).save(clinic);
    }

    @Test
    void testDeleteClinic() {
        serviceSer.deleteClinic(1L);
        verify(otherClinicRepository, times(1)).deleteById(1L);
    }
}
