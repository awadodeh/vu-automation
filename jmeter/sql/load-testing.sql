/* 

values are currently set up for the 2018 Fall term (0915)

The values in the enrollable classes result set do not need double quote enclosures.

The expected file names are classesToEnroll.csv , keyword-search.csv , students.csv

*/ 


-- Enrollable Classes
-- classesToEnroll.csv
select class_nbr 
  from ps_class_tbl class 
inner join ps_crse_catalog cat on (class.crse_id = cat.crse_id)
 where class.strm = '0915'
   and class.session_code = '1'     -- We only want "regular session" clases. 
   and class.acad_career = 'UGRD'
   and class.CLASS_STAT = 'A'
   AND class.schedule_print = 'Y'
   and class.consent = 'N'          -- Exclude instructor and department consent 
   and class.instruction_mode = 'P' -- Only get "in person" mode of instruction
   and class.ssr_component = 'LEC'  -- Only get lecture components. This really reduces the overall number of classes but they ones that are returned are much more enrollable. 
   and cat.EFF_STATUS = 'A' 
   and cat.effdt = (select max(effdt) from ps_crse_catalog where effdt <= SYSDATE and class.crse_id = crse_id)
order by dbms_random.random;


-- Keyword search query
-- keyword-search.csv
-- uses tab as seperator   
set define off;
select distinct 
  replace(class.subject, '&', '&amp;'),
  replace(subject.descrformal, '&', '&amp;'),
  class.CATALOG_NBR,
  replace(cat.COURSE_TITLE_LONG, '&', '&amp;'),
  replace(class.ACAD_GROUP, '&', '&amp;'),
  class.ssr_component,
  class.acad_career,
  class.instruction_mode
  from ps_class_tbl class
inner join  ps_subject_tbl subject on 
(
   class.institution = subject.institution and
   class.subject = subject.subject
)
inner join ps_crse_catalog cat on 
(
   class.CRSE_ID = cat.crse_id
)
where strm = '0915' 
order by dbms_random.random;

-- Enrollable Students
-- students.csv
select s.emplid, epi.vunet_id, names.FIRST_NAME, names.last_name
  from vuadm.STDNT_STATUS_MVW s
  inner join vuadm.EPI_IDENTITY_STUREC_BIT epi on (s.emplid = epi.student_emplid)
  inner join SYSADM.PS_STDNT_CAR_TERM sct on (s.emplid = sct.emplid and sct.acad_career = 'UGRD') 
  inner join vuadm.DISPLAY_PS_NAMES_VW names on  (s.emplid = names.emplid)
  inner join vuadm.stdnt_acad_primary_mvw p on (s.emplid = p.emplid)
 where sct.strm = '0915'
 
   -- Makes sure the student has ONLY undergrad terms. Just to keep things simple. 
   and s.emplid not in (
              select emplid 
                from SYSADM.PS_STDNT_CAR_TERM 
                where acad_career != 'UGRD'   
                  and strm = '0915')
  and s.active = 'Y'
  and p.PRIMARY_CLASSIFICATION in ('20', '30', '40') -- This only gets sophomores, juniors, and seniors 
order by dbms_random.random;

--------------------------------------------------------------------------------

-- The queries below are probably for data sets part of samplers that are either depracated or rarely used.


-- Gets course ids
-- course-ids.csv
select cat.crse_id, off.CRSE_OFFER_NBR
  from ps_crse_catalog  cat
 inner join SYSADM.PS_CRSE_OFFER off on
(
    cat.crse_id = off.crse_id
)
 where cat.effdt = (select max(effdt) from ps_crse_catalog where effdt <= SYSDATE and cat.crse_id = crse_id)
   and off.catalog_print     = 'Y'
   and off.course_approved     = 'A'
   and cat.EFF_STATUS = 'A'
order by dbms_random.random;

-- Course search
-- course-search.csv
set define off;
select replace(cat.course_title_long, '&', '&amp;'), replace(off.subject, '&', '&amp;'), off.catalog_nbr, replace(st.descrformal, '&', '&amp;')
  from ps_crse_catalog  cat
 inner join SYSADM.PS_CRSE_OFFER off on
(
    cat.crse_id = off.crse_id
)
INNER JOIN ps_subject_tbl st on 
(
    st.institution = off.institution
   and st.subject     = off.subject
)
 where cat.effdt = (select max(effdt) from ps_crse_catalog where effdt <= SYSDATE and cat.crse_id = crse_id)
   and off.catalog_print     = 'Y'
   and off.course_approved     = 'A'
   and cat.EFF_STATUS = 'A'
   and st.EFF_STATUS = 'A'
order by dbms_random.random;
