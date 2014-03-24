




<!DOCTYPE html>
<html class="  ">
  <head prefix="og: http://ogp.me/ns# fb: http://ogp.me/ns/fb# object: http://ogp.me/ns/object# article: http://ogp.me/ns/article# profile: http://ogp.me/ns/profile#">
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    
    
    <title>CommunityServiceWeb/CommunityService/src/org/CommunityService/Services/FacesConfigurationProvider.java at acbe65abcaf1d2d7c9560037967a470476fc6ce4 · davidhockey22/CommunityServiceWeb</title>
    <link rel="search" type="application/opensearchdescription+xml" href="/opensearch.xml" title="GitHub" />
    <link rel="fluid-icon" href="https://github.com/fluidicon.png" title="GitHub" />
    <link rel="apple-touch-icon" sizes="57x57" href="/apple-touch-icon-114.png" />
    <link rel="apple-touch-icon" sizes="114x114" href="/apple-touch-icon-114.png" />
    <link rel="apple-touch-icon" sizes="72x72" href="/apple-touch-icon-144.png" />
    <link rel="apple-touch-icon" sizes="144x144" href="/apple-touch-icon-144.png" />
    <meta property="fb:app_id" content="1401488693436528"/>

      <meta content="@github" name="twitter:site" /><meta content="summary" name="twitter:card" /><meta content="davidhockey22/CommunityServiceWeb" name="twitter:title" /><meta content="CommunityServiceWeb - Graphs are not indicative of work done in project. " name="twitter:description" /><meta content="https://avatars1.githubusercontent.com/u/3255922?s=400" name="twitter:image:src" />
<meta content="GitHub" property="og:site_name" /><meta content="object" property="og:type" /><meta content="https://avatars1.githubusercontent.com/u/3255922?s=400" property="og:image" /><meta content="davidhockey22/CommunityServiceWeb" property="og:title" /><meta content="https://github.com/davidhockey22/CommunityServiceWeb" property="og:url" /><meta content="CommunityServiceWeb - Graphs are not indicative of work done in project. " property="og:description" />

    <link rel="assets" href="https://github.global.ssl.fastly.net/">
    <link rel="conduit-xhr" href="https://ghconduit.com:25035/">
    <link rel="xhr-socket" href="/_sockets" />

    <meta name="msapplication-TileImage" content="/windows-tile.png" />
    <meta name="msapplication-TileColor" content="#ffffff" />
    <meta name="selected-link" value="repo_source" data-pjax-transient />
    <meta content="collector.githubapp.com" name="octolytics-host" /><meta content="collector-cdn.github.com" name="octolytics-script-host" /><meta content="github" name="octolytics-app-id" /><meta content="84AA93E6:64AE:6430283:53306DA2" name="octolytics-dimension-request_id" /><meta content="5533245" name="octolytics-actor-id" /><meta content="elaustinian" name="octolytics-actor-login" /><meta content="2ad33e790281bf91cd35b62ac3054fd3396126b7f19e9677b0cdf5da6c3bfc68" name="octolytics-actor-hash" />
    

    
    
    <link rel="icon" type="image/x-icon" href="https://github.global.ssl.fastly.net/favicon.ico" />

    <meta content="authenticity_token" name="csrf-param" />
<meta content="u3T/TN5nUM73Vw0frQAQ+WYjsJo92V2EJzYzcMlj5ZE=" name="csrf-token" />

    <link href="https://github.global.ssl.fastly.net/assets/github-b836a3967c04ec8b8bfdd101d2226064cb18f45d.css" media="all" rel="stylesheet" type="text/css" />
    <link href="https://github.global.ssl.fastly.net/assets/github2-91575e4b8f601c887ca6b13c6f78d06f03af775f.css" media="all" rel="stylesheet" type="text/css" />
    


        <script crossorigin="anonymous" src="https://github.global.ssl.fastly.net/assets/frameworks-1fdcfaa86258d75070622ace871ef84ed6e72cf0.js" type="text/javascript"></script>
        <script async="async" crossorigin="anonymous" src="https://github.global.ssl.fastly.net/assets/github-c4826f791f5000d38fe5d149aa9c9e2dc9b14fde.js" type="text/javascript"></script>
        
        
      <meta http-equiv="x-pjax-version" content="c6afddfef441d4510cef2acc0684722e">

        <link data-pjax-transient rel='permalink' href='/davidhockey22/CommunityServiceWeb/blob/acbe65abcaf1d2d7c9560037967a470476fc6ce4/CommunityService/src/org/CommunityService/Services/FacesConfigurationProvider.java'>

  <meta name="description" content="CommunityServiceWeb - Graphs are not indicative of work done in project. " />

  <meta content="3255922" name="octolytics-dimension-user_id" /><meta content="davidhockey22" name="octolytics-dimension-user_login" /><meta content="12771875" name="octolytics-dimension-repository_id" /><meta content="davidhockey22/CommunityServiceWeb" name="octolytics-dimension-repository_nwo" /><meta content="true" name="octolytics-dimension-repository_public" /><meta content="false" name="octolytics-dimension-repository_is_fork" /><meta content="12771875" name="octolytics-dimension-repository_network_root_id" /><meta content="davidhockey22/CommunityServiceWeb" name="octolytics-dimension-repository_network_root_nwo" />
  <link href="https://github.com/davidhockey22/CommunityServiceWeb/commits/acbe65abcaf1d2d7c9560037967a470476fc6ce4.atom" rel="alternate" title="Recent Commits to CommunityServiceWeb:acbe65abcaf1d2d7c9560037967a470476fc6ce4" type="application/atom+xml" />

  </head>


  <body class="logged_in  env-production windows vis-public page-blob">
    <a href="#start-of-content" class="accessibility-aid js-skip-to-content">Skip to content</a>
    <div class="wrapper">
      
      
      
      


      <div class="header header-logged-in true">
  <div class="container clearfix">

    <a class="header-logo-invertocat" href="https://github.com/">
  <span class="mega-octicon octicon-mark-github"></span>
</a>

    
    <a href="/davidhockey22/CommunityServiceWeb/notifications" aria-label="You have unread notifications in this repository" class="notification-indicator tooltipped tooltipped-s contextually-unread" data-gotokey="n">
        <span class="mail-status unread"></span>
</a>

      <div class="command-bar js-command-bar  in-repository">
          <form accept-charset="UTF-8" action="/search" class="command-bar-form" id="top_search_form" method="get">

<div class="commandbar">
  <span class="message"></span>
  <input type="text" data-hotkey="/ s" name="q" id="js-command-bar-field" placeholder="Search or type a command" tabindex="1" autocapitalize="off"
    
    data-username="elaustinian"
      data-repo="davidhockey22/CommunityServiceWeb"
      data-branch="acbe65abcaf1d2d7c9560037967a470476fc6ce4"
      data-sha="62dbc96cf5c99525516d3739f5ad7588cbdd4864"
  >
  <div class="display hidden"></div>
</div>

    <input type="hidden" name="nwo" value="davidhockey22/CommunityServiceWeb" />

    <div class="select-menu js-menu-container js-select-menu search-context-select-menu">
      <span class="minibutton select-menu-button js-menu-target" role="button" aria-haspopup="true">
        <span class="js-select-button">This repository</span>
      </span>

      <div class="select-menu-modal-holder js-menu-content js-navigation-container" aria-hidden="true">
        <div class="select-menu-modal">

          <div class="select-menu-item js-navigation-item js-this-repository-navigation-item selected">
            <span class="select-menu-item-icon octicon octicon-check"></span>
            <input type="radio" class="js-search-this-repository" name="search_target" value="repository" checked="checked" />
            <div class="select-menu-item-text js-select-button-text">This repository</div>
          </div> <!-- /.select-menu-item -->

          <div class="select-menu-item js-navigation-item js-all-repositories-navigation-item">
            <span class="select-menu-item-icon octicon octicon-check"></span>
            <input type="radio" name="search_target" value="global" />
            <div class="select-menu-item-text js-select-button-text">All repositories</div>
          </div> <!-- /.select-menu-item -->

        </div>
      </div>
    </div>

  <span class="help tooltipped tooltipped-s" aria-label="Show command bar help">
    <span class="octicon octicon-question"></span>
  </span>


  <input type="hidden" name="ref" value="cmdform">

</form>
        <ul class="top-nav">
          <li class="explore"><a href="/explore">Explore</a></li>
            <li><a href="https://gist.github.com">Gist</a></li>
            <li><a href="/blog">Blog</a></li>
          <li><a href="https://help.github.com">Help</a></li>
        </ul>
      </div>

    


  <ul id="user-links">
    <li>
      <a href="/elaustinian" class="name">
        <img alt="elaustinian" class=" js-avatar" data-user="5533245" height="20" src="https://avatars0.githubusercontent.com/u/5533245?s=140" width="20" /> elaustinian
      </a>
    </li>

    <li class="new-menu dropdown-toggle js-menu-container">
      <a href="#" class="js-menu-target tooltipped tooltipped-s" aria-label="Create new...">
        <span class="octicon octicon-plus"></span>
        <span class="dropdown-arrow"></span>
      </a>

      <div class="new-menu-content js-menu-content">
      </div>
    </li>

    <li>
      <a href="/settings/profile" id="account_settings"
        class="tooltipped tooltipped-s"
        aria-label="Account settings ">
        <span class="octicon octicon-tools"></span>
      </a>
    </li>
    <li>
      <a class="tooltipped tooltipped-s" href="/logout" data-method="post" id="logout" aria-label="Sign out">
        <span class="octicon octicon-log-out"></span>
      </a>
    </li>

  </ul>

<div class="js-new-dropdown-contents hidden">
  

<ul class="dropdown-menu">
  <li>
    <a href="/new"><span class="octicon octicon-repo-create"></span> New repository</a>
  </li>
  <li>
    <a href="/organizations/new"><span class="octicon octicon-organization"></span> New organization</a>
  </li>


    <li class="section-title">
      <span title="davidhockey22/CommunityServiceWeb">This repository</span>
    </li>
      <li>
        <a href="/davidhockey22/CommunityServiceWeb/issues/new"><span class="octicon octicon-issue-opened"></span> New issue</a>
      </li>
</ul>

</div>


    
  </div>
</div>

      

        



      <div id="start-of-content" class="accessibility-aid"></div>
          <div class="site" itemscope itemtype="http://schema.org/WebPage">
    
    <div class="pagehead repohead instapaper_ignore readability-menu">
      <div class="container">
        

<ul class="pagehead-actions">

    <li class="subscription">
      <form accept-charset="UTF-8" action="/notifications/subscribe" class="js-social-container" data-autosubmit="true" data-remote="true" method="post"><div style="margin:0;padding:0;display:inline"><input name="authenticity_token" type="hidden" value="u3T/TN5nUM73Vw0frQAQ+WYjsJo92V2EJzYzcMlj5ZE=" /></div>  <input id="repository_id" name="repository_id" type="hidden" value="12771875" />

    <div class="select-menu js-menu-container js-select-menu">
      <a class="social-count js-social-count" href="/davidhockey22/CommunityServiceWeb/watchers">
        3
      </a>
      <span class="minibutton select-menu-button with-count js-menu-target" role="button" tabindex="0" aria-haspopup="true">
        <span class="js-select-button">
          <span class="octicon octicon-eye-unwatch"></span>
          Unwatch
        </span>
      </span>

      <div class="select-menu-modal-holder">
        <div class="select-menu-modal subscription-menu-modal js-menu-content" aria-hidden="true">
          <div class="select-menu-header">
            <span class="select-menu-title">Notification status</span>
            <span class="octicon octicon-remove-close js-menu-close"></span>
          </div> <!-- /.select-menu-header -->

          <div class="select-menu-list js-navigation-container" role="menu">

            <div class="select-menu-item js-navigation-item " role="menuitem" tabindex="0">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <div class="select-menu-item-text">
                <input id="do_included" name="do" type="radio" value="included" />
                <h4>Not watching</h4>
                <span class="description">You only receive notifications for conversations in which you participate or are @mentioned.</span>
                <span class="js-select-button-text hidden-select-button-text">
                  <span class="octicon octicon-eye-watch"></span>
                  Watch
                </span>
              </div>
            </div> <!-- /.select-menu-item -->

            <div class="select-menu-item js-navigation-item selected" role="menuitem" tabindex="0">
              <span class="select-menu-item-icon octicon octicon octicon-check"></span>
              <div class="select-menu-item-text">
                <input checked="checked" id="do_subscribed" name="do" type="radio" value="subscribed" />
                <h4>Watching</h4>
                <span class="description">You receive notifications for all conversations in this repository.</span>
                <span class="js-select-button-text hidden-select-button-text">
                  <span class="octicon octicon-eye-unwatch"></span>
                  Unwatch
                </span>
              </div>
            </div> <!-- /.select-menu-item -->

            <div class="select-menu-item js-navigation-item " role="menuitem" tabindex="0">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <div class="select-menu-item-text">
                <input id="do_ignore" name="do" type="radio" value="ignore" />
                <h4>Ignoring</h4>
                <span class="description">You do not receive any notifications for conversations in this repository.</span>
                <span class="js-select-button-text hidden-select-button-text">
                  <span class="octicon octicon-mute"></span>
                  Stop ignoring
                </span>
              </div>
            </div> <!-- /.select-menu-item -->

          </div> <!-- /.select-menu-list -->

        </div> <!-- /.select-menu-modal -->
      </div> <!-- /.select-menu-modal-holder -->
    </div> <!-- /.select-menu -->

</form>
    </li>

  <li>
  

  <div class="js-toggler-container js-social-container starring-container ">
    <a href="/davidhockey22/CommunityServiceWeb/unstar"
      class="minibutton with-count js-toggler-target star-button starred"
      aria-label="Unstar this repository" title="Unstar davidhockey22/CommunityServiceWeb" data-remote="true" data-method="post" rel="nofollow">
      <span class="octicon octicon-star-delete"></span><span class="text">Unstar</span>
    </a>

    <a href="/davidhockey22/CommunityServiceWeb/star"
      class="minibutton with-count js-toggler-target star-button unstarred"
      aria-label="Star this repository" title="Star davidhockey22/CommunityServiceWeb" data-remote="true" data-method="post" rel="nofollow">
      <span class="octicon octicon-star"></span><span class="text">Star</span>
    </a>

      <a class="social-count js-social-count" href="/davidhockey22/CommunityServiceWeb/stargazers">
        0
      </a>
  </div>

  </li>


        <li>
          <a href="/davidhockey22/CommunityServiceWeb/fork" class="minibutton with-count js-toggler-target fork-button lighter tooltipped-n" title="Fork your own copy of davidhockey22/CommunityServiceWeb to your account" aria-label="Fork your own copy of davidhockey22/CommunityServiceWeb to your account" rel="nofollow" data-method="post">
            <span class="octicon octicon-git-branch-create"></span><span class="text">Fork</span>
          </a>
          <a href="/davidhockey22/CommunityServiceWeb/network" class="social-count">0</a>
        </li>


</ul>

        <h1 itemscope itemtype="http://data-vocabulary.org/Breadcrumb" class="entry-title public">
          <span class="repo-label"><span>public</span></span>
          <span class="mega-octicon octicon-repo"></span>
          <span class="author">
            <a href="/davidhockey22" class="url fn" itemprop="url" rel="author"><span itemprop="title">davidhockey22</span></a>
          </span>
          <span class="repohead-name-divider">/</span>
          <strong><a href="/davidhockey22/CommunityServiceWeb" class="js-current-repository js-repo-home-link">CommunityServiceWeb</a></strong>

          <span class="page-context-loader">
            <img alt="Octocat-spinner-32" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
          </span>

        </h1>
      </div><!-- /.container -->
    </div><!-- /.repohead -->

    <div class="container">
      <div class="repository-with-sidebar repo-container new-discussion-timeline js-new-discussion-timeline  ">
        <div class="repository-sidebar clearfix">
            

<div class="sunken-menu vertical-right repo-nav js-repo-nav js-repository-container-pjax js-octicon-loaders">
  <div class="sunken-menu-contents">
    <ul class="sunken-menu-group">
      <li class="tooltipped tooltipped-w" aria-label="Code">
        <a href="/davidhockey22/CommunityServiceWeb" aria-label="Code" class="selected js-selected-navigation-item sunken-menu-item" data-gotokey="c" data-pjax="true" data-selected-links="repo_source repo_downloads repo_commits repo_tags repo_branches /davidhockey22/CommunityServiceWeb">
          <span class="octicon octicon-code"></span> <span class="full-word">Code</span>
          <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>      </li>

        <li class="tooltipped tooltipped-w" aria-label="Issues">
          <a href="/davidhockey22/CommunityServiceWeb/issues" aria-label="Issues" class="js-selected-navigation-item sunken-menu-item js-disable-pjax" data-gotokey="i" data-selected-links="repo_issues /davidhockey22/CommunityServiceWeb/issues">
            <span class="octicon octicon-issue-opened"></span> <span class="full-word">Issues</span>
            <span class='counter'>10</span>
            <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>        </li>

      <li class="tooltipped tooltipped-w" aria-label="Pull Requests">
        <a href="/davidhockey22/CommunityServiceWeb/pulls" aria-label="Pull Requests" class="js-selected-navigation-item sunken-menu-item js-disable-pjax" data-gotokey="p" data-selected-links="repo_pulls /davidhockey22/CommunityServiceWeb/pulls">
            <span class="octicon octicon-git-pull-request"></span> <span class="full-word">Pull Requests</span>
            <span class='counter'>0</span>
            <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>      </li>


        <li class="tooltipped tooltipped-w" aria-label="Wiki">
          <a href="/davidhockey22/CommunityServiceWeb/wiki" aria-label="Wiki" class="js-selected-navigation-item sunken-menu-item" data-pjax="true" data-selected-links="repo_wiki /davidhockey22/CommunityServiceWeb/wiki">
            <span class="octicon octicon-book"></span> <span class="full-word">Wiki</span>
            <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>        </li>
    </ul>
    <div class="sunken-menu-separator"></div>
    <ul class="sunken-menu-group">

      <li class="tooltipped tooltipped-w" aria-label="Pulse">
        <a href="/davidhockey22/CommunityServiceWeb/pulse" aria-label="Pulse" class="js-selected-navigation-item sunken-menu-item" data-pjax="true" data-selected-links="pulse /davidhockey22/CommunityServiceWeb/pulse">
          <span class="octicon octicon-pulse"></span> <span class="full-word">Pulse</span>
          <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>      </li>

      <li class="tooltipped tooltipped-w" aria-label="Graphs">
        <a href="/davidhockey22/CommunityServiceWeb/graphs" aria-label="Graphs" class="js-selected-navigation-item sunken-menu-item" data-pjax="true" data-selected-links="repo_graphs repo_contributors /davidhockey22/CommunityServiceWeb/graphs">
          <span class="octicon octicon-graph"></span> <span class="full-word">Graphs</span>
          <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>      </li>

      <li class="tooltipped tooltipped-w" aria-label="Network">
        <a href="/davidhockey22/CommunityServiceWeb/network" aria-label="Network" class="js-selected-navigation-item sunken-menu-item js-disable-pjax" data-selected-links="repo_network /davidhockey22/CommunityServiceWeb/network">
          <span class="octicon octicon-git-branch"></span> <span class="full-word">Network</span>
          <img alt="Octocat-spinner-32" class="mini-loader" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32.gif" width="16" />
</a>      </li>
    </ul>


  </div>
</div>

              <div class="only-with-full-nav">
                

  

<div class="clone-url open"
  data-protocol-type="http"
  data-url="/users/set_protocol?protocol_selector=http&amp;protocol_type=push">
  <h3><strong>HTTPS</strong> clone URL</h3>
  <div class="clone-url-box">
    <input type="text" class="clone js-url-field"
           value="https://github.com/davidhockey22/CommunityServiceWeb.git" readonly="readonly">

    <span aria-label="copy to clipboard" class="js-zeroclipboard url-box-clippy minibutton zeroclipboard-button" data-clipboard-text="https://github.com/davidhockey22/CommunityServiceWeb.git" data-copied-hint="copied!"><span class="octicon octicon-clippy"></span></span>
  </div>
</div>

  

<div class="clone-url "
  data-protocol-type="ssh"
  data-url="/users/set_protocol?protocol_selector=ssh&amp;protocol_type=push">
  <h3><strong>SSH</strong> clone URL</h3>
  <div class="clone-url-box">
    <input type="text" class="clone js-url-field"
           value="git@github.com:davidhockey22/CommunityServiceWeb.git" readonly="readonly">

    <span aria-label="copy to clipboard" class="js-zeroclipboard url-box-clippy minibutton zeroclipboard-button" data-clipboard-text="git@github.com:davidhockey22/CommunityServiceWeb.git" data-copied-hint="copied!"><span class="octicon octicon-clippy"></span></span>
  </div>
</div>

  

<div class="clone-url "
  data-protocol-type="subversion"
  data-url="/users/set_protocol?protocol_selector=subversion&amp;protocol_type=push">
  <h3><strong>Subversion</strong> checkout URL</h3>
  <div class="clone-url-box">
    <input type="text" class="clone js-url-field"
           value="https://github.com/davidhockey22/CommunityServiceWeb" readonly="readonly">

    <span aria-label="copy to clipboard" class="js-zeroclipboard url-box-clippy minibutton zeroclipboard-button" data-clipboard-text="https://github.com/davidhockey22/CommunityServiceWeb" data-copied-hint="copied!"><span class="octicon octicon-clippy"></span></span>
  </div>
</div>


<p class="clone-options">You can clone with
      <a href="#" class="js-clone-selector" data-protocol="http">HTTPS</a>,
      <a href="#" class="js-clone-selector" data-protocol="ssh">SSH</a>,
      or <a href="#" class="js-clone-selector" data-protocol="subversion">Subversion</a>.
  <span class="help tooltipped tooltipped-n" aria-label="Get help on which URL is right for you.">
    <a href="https://help.github.com/articles/which-remote-url-should-i-use">
    <span class="octicon octicon-question"></span>
    </a>
  </span>
</p>


  <a href="github-windows://openRepo/https://github.com/davidhockey22/CommunityServiceWeb" class="minibutton sidebar-button" title="Save davidhockey22/CommunityServiceWeb to your computer and use it in GitHub Desktop." aria-label="Save davidhockey22/CommunityServiceWeb to your computer and use it in GitHub Desktop.">
    <span class="octicon octicon-device-desktop"></span>
    Clone in Desktop
  </a>

                <a href="/davidhockey22/CommunityServiceWeb/archive/acbe65abcaf1d2d7c9560037967a470476fc6ce4.zip"
                   class="minibutton sidebar-button"
                   aria-label="Download davidhockey22/CommunityServiceWeb as a zip file"
                   title="Download davidhockey22/CommunityServiceWeb as a zip file"
                   rel="nofollow">
                  <span class="octicon octicon-cloud-download"></span>
                  Download ZIP
                </a>
              </div>
        </div><!-- /.repository-sidebar -->

        <div id="js-repo-pjax-container" class="repository-content context-loader-container" data-pjax-container>
          


<!-- blob contrib key: blob_contributors:v21:2cb3b9ac94b7f0375f5cfc3fd08ea38a -->

<p title="This is a placeholder element" class="js-history-link-replace hidden"></p>

<a href="/davidhockey22/CommunityServiceWeb/find/acbe65abcaf1d2d7c9560037967a470476fc6ce4" data-pjax data-hotkey="t" class="js-show-file-finder" style="display:none">Show File Finder</a>

<div class="file-navigation">
  

<div class="select-menu js-menu-container js-select-menu" >
  <span class="minibutton select-menu-button js-menu-target" data-hotkey="w"
    data-master-branch="master"
    data-ref=""
    role="button" aria-label="Switch branches or tags" tabindex="0" aria-haspopup="true">
    <span class="octicon octicon-git-branch"></span>
    <i>tree:</i>
    <span class="js-select-button">acbe65abca</span>
  </span>

  <div class="select-menu-modal-holder js-menu-content js-navigation-container" data-pjax aria-hidden="true">

    <div class="select-menu-modal">
      <div class="select-menu-header">
        <span class="select-menu-title">Switch branches/tags</span>
        <span class="octicon octicon-remove-close js-menu-close"></span>
      </div> <!-- /.select-menu-header -->

      <div class="select-menu-filters">
        <div class="select-menu-text-filter">
          <input type="text" aria-label="Find or create a branch…" id="context-commitish-filter-field" class="js-filterable-field js-navigation-enable" placeholder="Find or create a branch…">
        </div>
        <div class="select-menu-tabs">
          <ul>
            <li class="select-menu-tab">
              <a href="#" data-tab-filter="branches" class="js-select-menu-tab">Branches</a>
            </li>
            <li class="select-menu-tab">
              <a href="#" data-tab-filter="tags" class="js-select-menu-tab">Tags</a>
            </li>
          </ul>
        </div><!-- /.select-menu-tabs -->
      </div><!-- /.select-menu-filters -->

      <div class="select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket" data-tab-filter="branches">

        <div data-filterable-for="context-commitish-filter-field" data-filterable-type="substring">


            <div class="select-menu-item js-navigation-item ">
              <span class="select-menu-item-icon octicon octicon-check"></span>
              <a href="/davidhockey22/CommunityServiceWeb/blob/master/CommunityService/src/org/CommunityService/Services/FacesConfigurationProvider.java"
                 data-name="master"
                 data-skip-pjax="true"
                 rel="nofollow"
                 class="js-navigation-open select-menu-item-text js-select-button-text css-truncate-target"
                 title="master">master</a>
            </div> <!-- /.select-menu-item -->
        </div>

          <form accept-charset="UTF-8" action="/davidhockey22/CommunityServiceWeb/branches" class="js-create-branch select-menu-item select-menu-new-item-form js-navigation-item js-new-item-form" method="post"><div style="margin:0;padding:0;display:inline"><input name="authenticity_token" type="hidden" value="u3T/TN5nUM73Vw0frQAQ+WYjsJo92V2EJzYzcMlj5ZE=" /></div>
            <span class="octicon octicon-git-branch-create select-menu-item-icon"></span>
            <div class="select-menu-item-text">
              <h4>Create branch: <span class="js-new-item-name"></span></h4>
              <span class="description">from ‘acbe65a’</span>
            </div>
            <input type="hidden" name="name" id="name" class="js-new-item-value">
            <input type="hidden" name="branch" id="branch" value="acbe65abcaf1d2d7c9560037967a470476fc6ce4" />
            <input type="hidden" name="path" id="path" value="CommunityService/src/org/CommunityService/Services/FacesConfigurationProvider.java" />
          </form> <!-- /.select-menu-item -->

      </div> <!-- /.select-menu-list -->

      <div class="select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket" data-tab-filter="tags">
        <div data-filterable-for="context-commitish-filter-field" data-filterable-type="substring">


        </div>

        <div class="select-menu-no-results">Nothing to show</div>
      </div> <!-- /.select-menu-list -->

    </div> <!-- /.select-menu-modal -->
  </div> <!-- /.select-menu-modal-holder -->
</div> <!-- /.select-menu -->

  <div class="breadcrumb">
    <span class='repo-root js-repo-root'><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/davidhockey22/CommunityServiceWeb/tree/acbe65abcaf1d2d7c9560037967a470476fc6ce4" data-branch="acbe65abcaf1d2d7c9560037967a470476fc6ce4" data-direction="back" data-pjax="true" itemscope="url" rel="nofollow"><span itemprop="title">CommunityServiceWeb</span></a></span></span><span class="separator"> / </span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/davidhockey22/CommunityServiceWeb/tree/acbe65abcaf1d2d7c9560037967a470476fc6ce4/CommunityService" data-branch="acbe65abcaf1d2d7c9560037967a470476fc6ce4" data-direction="back" data-pjax="true" itemscope="url" rel="nofollow"><span itemprop="title">CommunityService</span></a></span><span class="separator"> / </span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/davidhockey22/CommunityServiceWeb/tree/acbe65abcaf1d2d7c9560037967a470476fc6ce4/CommunityService/src" data-branch="acbe65abcaf1d2d7c9560037967a470476fc6ce4" data-direction="back" data-pjax="true" itemscope="url" rel="nofollow"><span itemprop="title">src</span></a></span><span class="separator"> / </span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/davidhockey22/CommunityServiceWeb/tree/acbe65abcaf1d2d7c9560037967a470476fc6ce4/CommunityService/src/org" data-branch="acbe65abcaf1d2d7c9560037967a470476fc6ce4" data-direction="back" data-pjax="true" itemscope="url" rel="nofollow"><span itemprop="title">org</span></a></span><span class="separator"> / </span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/davidhockey22/CommunityServiceWeb/tree/acbe65abcaf1d2d7c9560037967a470476fc6ce4/CommunityService/src/org/CommunityService" data-branch="acbe65abcaf1d2d7c9560037967a470476fc6ce4" data-direction="back" data-pjax="true" itemscope="url" rel="nofollow"><span itemprop="title">CommunityService</span></a></span><span class="separator"> / </span><span itemscope="" itemtype="http://data-vocabulary.org/Breadcrumb"><a href="/davidhockey22/CommunityServiceWeb/tree/acbe65abcaf1d2d7c9560037967a470476fc6ce4/CommunityService/src/org/CommunityService/Services" data-branch="acbe65abcaf1d2d7c9560037967a470476fc6ce4" data-direction="back" data-pjax="true" itemscope="url" rel="nofollow"><span itemprop="title">Services</span></a></span><span class="separator"> / </span><strong class="final-path">FacesConfigurationProvider.java</strong> <span aria-label="copy to clipboard" class="js-zeroclipboard minibutton zeroclipboard-button" data-clipboard-text="CommunityService/src/org/CommunityService/Services/FacesConfigurationProvider.java" data-copied-hint="copied!"><span class="octicon octicon-clippy"></span></span>
  </div>
</div>


  <div class="commit commit-loader file-history-tease js-deferred-content" data-url="/davidhockey22/CommunityServiceWeb/contributors/acbe65abcaf1d2d7c9560037967a470476fc6ce4/CommunityService/src/org/CommunityService/Services/FacesConfigurationProvider.java">
    Fetching contributors…

    <div class="participation">
      <p class="loader-loading"><img alt="Octocat-spinner-32-eaf2f5" height="16" src="https://github.global.ssl.fastly.net/images/spinners/octocat-spinner-32-EAF2F5.gif" width="16" /></p>
      <p class="loader-error">Cannot retrieve contributors at this time</p>
    </div>
  </div>

<div class="file-box">
  <div class="file">
    <div class="meta clearfix">
      <div class="info file-name">
        <span class="icon"><b class="octicon octicon-file-text"></b></span>
        <span class="mode" title="File Mode">file</span>
        <span class="meta-divider"></span>
          <span>34 lines (25 sloc)</span>
          <span class="meta-divider"></span>
        <span>1.139 kb</span>
      </div>
      <div class="actions">
        <div class="button-group">
              <a class="minibutton disabled tooltipped tooltipped-w" href="#"
                 aria-label="You must be on a branch to make or propose changes to this file">Edit</a>
          <a href="/davidhockey22/CommunityServiceWeb/raw/acbe65abcaf1d2d7c9560037967a470476fc6ce4/CommunityService/src/org/CommunityService/Services/FacesConfigurationProvider.java" class="button minibutton " id="raw-url">Raw</a>
            <a href="/davidhockey22/CommunityServiceWeb/blame/acbe65abcaf1d2d7c9560037967a470476fc6ce4/CommunityService/src/org/CommunityService/Services/FacesConfigurationProvider.java" class="button minibutton js-update-url-with-hash">Blame</a>
          <a href="/davidhockey22/CommunityServiceWeb/commits/acbe65abcaf1d2d7c9560037967a470476fc6ce4/CommunityService/src/org/CommunityService/Services/FacesConfigurationProvider.java" class="button minibutton " rel="nofollow">History</a>
        </div><!-- /.button-group -->
          <a class="minibutton danger disabled empty-icon tooltipped tooltipped-w" href="#"
             aria-label="You must be on a branch to make or propose changes to this file">
          Delete
        </a>
      </div><!-- /.actions -->
    </div>
        <div class="blob-wrapper data type-java js-blob-data">
        <table class="file-code file-diff tab-size-8">
          <tr class="file-code-line">
            <td class="blob-line-nums">
              <span id="L1" rel="#L1">1</span>
<span id="L2" rel="#L2">2</span>
<span id="L3" rel="#L3">3</span>
<span id="L4" rel="#L4">4</span>
<span id="L5" rel="#L5">5</span>
<span id="L6" rel="#L6">6</span>
<span id="L7" rel="#L7">7</span>
<span id="L8" rel="#L8">8</span>
<span id="L9" rel="#L9">9</span>
<span id="L10" rel="#L10">10</span>
<span id="L11" rel="#L11">11</span>
<span id="L12" rel="#L12">12</span>
<span id="L13" rel="#L13">13</span>
<span id="L14" rel="#L14">14</span>
<span id="L15" rel="#L15">15</span>
<span id="L16" rel="#L16">16</span>
<span id="L17" rel="#L17">17</span>
<span id="L18" rel="#L18">18</span>
<span id="L19" rel="#L19">19</span>
<span id="L20" rel="#L20">20</span>
<span id="L21" rel="#L21">21</span>
<span id="L22" rel="#L22">22</span>
<span id="L23" rel="#L23">23</span>
<span id="L24" rel="#L24">24</span>
<span id="L25" rel="#L25">25</span>
<span id="L26" rel="#L26">26</span>
<span id="L27" rel="#L27">27</span>
<span id="L28" rel="#L28">28</span>
<span id="L29" rel="#L29">29</span>
<span id="L30" rel="#L30">30</span>
<span id="L31" rel="#L31">31</span>
<span id="L32" rel="#L32">32</span>
<span id="L33" rel="#L33">33</span>

            </td>
            <td class="blob-line-code"><div class="code-body highlight"><pre><div class='line' id='LC1'><span class="kn">package</span> <span class="n">org</span><span class="o">.</span><span class="na">CommunityService</span><span class="o">.</span><span class="na">Services</span><span class="o">;</span></div><div class='line' id='LC2'><br/></div><div class='line' id='LC3'><span class="kn">import</span> <span class="nn">javax.servlet.ServletContext</span><span class="o">;</span></div><div class='line' id='LC4'><br/></div><div class='line' id='LC5'><span class="kn">import</span> <span class="nn">org.ocpsoft.rewrite.annotation.RewriteConfiguration</span><span class="o">;</span></div><div class='line' id='LC6'><span class="kn">import</span> <span class="nn">org.ocpsoft.rewrite.config.Configuration</span><span class="o">;</span></div><div class='line' id='LC7'><span class="kn">import</span> <span class="nn">org.ocpsoft.rewrite.config.ConfigurationBuilder</span><span class="o">;</span></div><div class='line' id='LC8'><span class="kn">import</span> <span class="nn">org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider</span><span class="o">;</span></div><div class='line' id='LC9'><span class="kn">import</span> <span class="nn">org.ocpsoft.rewrite.servlet.config.rule.Join</span><span class="o">;</span></div><div class='line' id='LC10'><br/></div><div class='line' id='LC11'><span class="nd">@RewriteConfiguration</span></div><div class='line' id='LC12'><span class="kd">public</span> <span class="kd">class</span> <span class="nc">FacesConfigurationProvider</span> <span class="kd">extends</span> <span class="n">HttpConfigurationProvider</span><span class="o">{</span></div><div class='line' id='LC13'><br/></div><div class='line' id='LC14'>	<span class="nd">@Override</span></div><div class='line' id='LC15'>	<span class="kd">public</span> <span class="n">Configuration</span> <span class="nf">getConfiguration</span><span class="o">(</span><span class="kd">final</span> <span class="n">ServletContext</span> <span class="n">context</span><span class="o">)</span> <span class="o">{</span></div><div class='line' id='LC16'>		<span class="k">return</span> <span class="n">ConfigurationBuilder</span><span class="o">.</span><span class="na">begin</span><span class="o">()</span></div><div class='line' id='LC17'>				<span class="o">.</span><span class="na">addRule</span><span class="o">(</span><span class="n">Join</span><span class="o">.</span><span class="na">path</span><span class="o">(</span><span class="s">&quot;/group/{groupId}&quot;</span><span class="o">).</span><span class="na">to</span><span class="o">(</span><span class="s">&quot;/Web/ViewGroup.xhtml&quot;</span><span class="o">))</span></div><div class='line' id='LC18'><br/></div><div class='line' id='LC19'>				<span class="o">.</span><span class="na">addRule</span><span class="o">(</span><span class="n">Join</span><span class="o">.</span><span class="na">path</span><span class="o">(</span><span class="s">&quot;/createGroup&quot;</span><span class="o">).</span><span class="na">to</span><span class="o">(</span><span class="s">&quot;/Web/NewGroup.xhtml&quot;</span><span class="o">))</span></div><div class='line' id='LC20'><br/></div><div class='line' id='LC21'>				<span class="o">.</span><span class="na">addRule</span><span class="o">(</span><span class="n">Join</span><span class="o">.</span><span class="na">path</span><span class="o">(</span><span class="s">&quot;/manageEvent/{eventId}&quot;</span><span class="o">).</span><span class="na">to</span><span class="o">(</span><span class="s">&quot;/Web/EditEvent.xhtml&quot;</span><span class="o">))</span></div><div class='line' id='LC22'>				<span class="o">.</span><span class="na">addRule</span><span class="o">(</span><span class="n">Join</span><span class="o">.</span><span class="na">path</span><span class="o">(</span><span class="s">&quot;/manageOrganization/{orgId}&quot;</span><span class="o">).</span><span class="na">to</span><span class="o">(</span><span class="s">&quot;/Web/EditOrganization.xhtml&quot;</span><span class="o">))</span></div><div class='line' id='LC23'>				<span class="o">.</span><span class="na">addRule</span><span class="o">(</span><span class="n">Join</span><span class="o">.</span><span class="na">path</span><span class="o">(</span><span class="s">&quot;/manageGroup/{groupId}&quot;</span><span class="o">).</span><span class="na">to</span><span class="o">(</span><span class="s">&quot;/Web/EditGroup.xhtml&quot;</span><span class="o">))</span></div><div class='line' id='LC24'>				<span class="o">;</span></div><div class='line' id='LC25'>	<span class="o">}</span></div><div class='line' id='LC26'><br/></div><div class='line' id='LC27'>	<span class="nd">@Override</span></div><div class='line' id='LC28'>	<span class="kd">public</span> <span class="kt">int</span> <span class="nf">priority</span><span class="o">()</span> <span class="o">{</span></div><div class='line' id='LC29'>		<span class="c1">// Using 10 so that if we want more configuration providers, we can add above or below this one</span></div><div class='line' id='LC30'>		<span class="k">return</span> <span class="mi">10</span><span class="o">;</span></div><div class='line' id='LC31'>	<span class="o">}</span></div><div class='line' id='LC32'><br/></div><div class='line' id='LC33'><span class="o">}</span></div></pre></div></td>
          </tr>
        </table>
  </div>

  </div>
</div>

<a href="#jump-to-line" rel="facebox[.linejump]" data-hotkey="l" class="js-jump-to-line" style="display:none">Jump to Line</a>
<div id="jump-to-line" style="display:none">
  <form accept-charset="UTF-8" class="js-jump-to-line-form">
    <input class="linejump-input js-jump-to-line-field" type="text" placeholder="Jump to line&hellip;" autofocus>
    <button type="submit" class="button">Go</button>
  </form>
</div>

        </div>

      </div><!-- /.repo-container -->
      <div class="modal-backdrop"></div>
    </div><!-- /.container -->
  </div><!-- /.site -->


    </div><!-- /.wrapper -->

      <div class="container">
  <div class="site-footer">
    <ul class="site-footer-links right">
      <li><a href="https://status.github.com/">Status</a></li>
      <li><a href="http://developer.github.com">API</a></li>
      <li><a href="http://training.github.com">Training</a></li>
      <li><a href="http://shop.github.com">Shop</a></li>
      <li><a href="/blog">Blog</a></li>
      <li><a href="/about">About</a></li>

    </ul>

    <a href="/">
      <span class="mega-octicon octicon-mark-github" title="GitHub"></span>
    </a>

    <ul class="site-footer-links">
      <li>&copy; 2014 <span title="0.05255s from github-fe117-cp1-prd.iad.github.net">GitHub</span>, Inc.</li>
        <li><a href="/site/terms">Terms</a></li>
        <li><a href="/site/privacy">Privacy</a></li>
        <li><a href="/security">Security</a></li>
        <li><a href="/contact">Contact</a></li>
    </ul>
  </div><!-- /.site-footer -->
</div><!-- /.container -->


    <div class="fullscreen-overlay js-fullscreen-overlay" id="fullscreen_overlay">
  <div class="fullscreen-container js-fullscreen-container">
    <div class="textarea-wrap">
      <textarea name="fullscreen-contents" id="fullscreen-contents" class="js-fullscreen-contents" placeholder="" data-suggester="fullscreen_suggester"></textarea>
    </div>
  </div>
  <div class="fullscreen-sidebar">
    <a href="#" class="exit-fullscreen js-exit-fullscreen tooltipped tooltipped-w" aria-label="Exit Zen Mode">
      <span class="mega-octicon octicon-screen-normal"></span>
    </a>
    <a href="#" class="theme-switcher js-theme-switcher tooltipped tooltipped-w"
      aria-label="Switch themes">
      <span class="octicon octicon-color-mode"></span>
    </a>
  </div>
</div>



    <div id="ajax-error-message" class="flash flash-error">
      <span class="octicon octicon-alert"></span>
      <a href="#" class="octicon octicon-remove-close close js-ajax-error-dismiss"></a>
      Something went wrong with that request. Please try again.
    </div>

  </body>
</html>

