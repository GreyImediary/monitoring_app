package ru.therapyapp.monitoringapp

import android.app.Application
import ru.therapyapp.data_bvas.DataBvasModule
import ru.therapyapp.feature_asdas_impl.AsdasModule
import ru.therapyapp.feature_bvas_impl.BvasModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.therapyapp.core_network.RetrofitModule
import ru.therapyapp.core_prefs.CorePrefsModule
import ru.therapyapp.data_auth.DataAuthModule
import ru.therapyapp.data_basdai.DataBasdaiModule
import ru.therapyapp.data_comments.CommentModule
import ru.therapyapp.data_doctor.DataDoctorModule
import ru.therapyapp.data_mkb.MkbModule
import ru.therapyapp.data_patient.DataPatientModule
import ru.therapyapp.data_questionnaire.QuestionnaireModule
import ru.therapyapp.data_questionnaire_answered.QuestionnaireAnsweredModule
import ru.therapyapp.data_request.DataRequestModule
import ru.therapyapp.data_sledai.SelenaSledaiModule
import ru.therapyapp.feature_answered_questionnaire_impl.QuestionnaireAnsweredScreenModule
import ru.therapyapp.feature_auth_impl.AuthModule
import ru.therapyapp.feature_basdai_impl.BasdaiModule
import ru.therapyapp.feature_current_patient_impl.CurrentPatientModule
import ru.therapyapp.feature_doctor_screen_impl.DoctorScreenModule
import ru.therapyapp.feature_patient_screen_impl.PatientScreenModule
import ru.therapyapp.feature_questionnaire_add_impl.QuestionnaireAddModule
import ru.therapyapp.feature_questionnaire_impl.QuestionnaireScreenModule
import ru.therapyapp.feature_sledai_impl.SelenaSledaiScreenModule
import ru.therapyapp.feature_user_data_impl.screen.UserDataModule

class App : Application() {
    private val modules by lazy {
        listOf(
            RetrofitModule().module,
            DataAuthModule().module,
            AuthModule().module,
            UserDataModule().module,
            DataDoctorModule().module,
            DataPatientModule().module,
            DataRequestModule().module,
            DataBasdaiModule().module,
            ru.therapyapp.data_asdas.DataAsdasModule().module,
            DataBvasModule().module,
            PatientScreenModule().module,
            DoctorScreenModule().module,
            CorePrefsModule().module,
            BasdaiModule().module,
            AsdasModule().module,
            BvasModule().module,
            CurrentPatientModule().module,
            CommentModule().module,
            QuestionnaireModule().module,
            QuestionnaireAnsweredModule().module,
            QuestionnaireAddModule().module,
            QuestionnaireAnsweredScreenModule().module,
            QuestionnaireScreenModule().module,
            MkbModule().module,
            SelenaSledaiModule().module,
            SelenaSledaiScreenModule().module,
        )
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(modules)
        }
    }
}