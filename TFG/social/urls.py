import os

from django.conf.urls import patterns, include, url
#from django.views.generic.simple import direct_to_template
from django.views.generic import TemplateView
from social import views


site_media = os.path.join(
    os.path.dirname(__file__), 'site_media'
)
# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    url(r'^$', views.main_page, name='social-main'),
    url(r'^user/follow$', views.user_follow, name='social-user-follow'),
    url(r'^user/unfollow$', views.user_unfollow, name='social-user-unfollow'),
    url(r'^user/(\w+)/$', views.user_page, name='social-auth-user'),
    url(r'^profile/$', views.user_profile, name='social-user-profile'),
    url(r'^login/$', 'django.contrib.auth.views.login', name='social-auth-login'),
    url(r'^logout/$', views.logout_page, name='social-auth-logout'),
    url(r'^site_media/(?P<path>.*)$', 'django.views.static.serve', {'document_root': site_media}),
    url(r'^register/$', views.register_page, name='social-auth-register'),
    url(r'^register/success/$', TemplateView.as_view(template_name='registration/register_success.html'), name='social-auth-register-success'),
    url(r'^tag/$', views.tag_save_page, name='tag-form'),
    
    url(r'^profile/$', views.user_profile, name='social-user-profile'),
    url(r'^timeline/$', views.timeline_page, name='social-timeline-page'),
    url(r'^users/(?P<username>\w+)$', views.user_page, name='social-user-page'),

    url(r'^users/$', views.users_list, name='social-users-list'),
    # Ajax
    url(r'^ajax/tag/autocomplete/$', views.ajax_tag_autocomplete, name='social-tag-autocomplete'),
    # Tag page
    #url(r'^tags/(?P<tag_name>\w+)/(?P<modeltype>\w+)/$', views.tag_page, name='social-tag-page'),
    url(r'^tags/(?P<tagname>[\w\+]+)/(?P<modeltype>\w+)$', views.tag_page, name='social-tag-page'),


    # Examples:
    # url(r'^$', 'Dunya.views.home', name='home'),
    # url(r'^Dunya/', include('Dunya.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    # url(r'^admin/', include(admin.site.urls)),
)
