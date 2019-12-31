package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.CompetitorDataSource;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationBean implements Serializable {

	@ManagedProperty("#{panelMenuBean}")
	private PanelMenuBean panelMenuBean;

	private static final long serialVersionUID = 1520318172495977648L;

	/**
	 * Redirect to login page.
	 * 
	 * @return Login page name.
	 */
	public String redirectToLogin() {
		return "/login.xhtml?faces-redirect=true";
	}

	/**
	 * Go to login page.
	 * 
	 * @return Login page name.
	 */
	public String toLogin() {
		return "/login.xhtml?faces-redirect=true";
	}

	public String redirectToLayout() {
		collapseAllMenuItems();
		return "/secured/layout/layout.xhtml?faces-redirect=true";
	}

	public String redirectToAddAccount() {
		collapseAllMenuItems();
		panelMenuBean.setAdminExpanded(true);
		return "/secured/layout/admin/addaccount.xhtml?faces-redirect=true";
	}

	public String redirectToAddUser() {
		collapseAllMenuItems();
		panelMenuBean.setAdminExpanded(true);
		return "/secured/layout/admin/adduser.xhtml?faces-redirect=true";
	}

	public String redirectToQuickstartGuide() {
		collapseAllMenuItems();
		panelMenuBean.setQuickstartExpanded(true);
		return "/secured/layout/documentation/quickstart.xhtml?faces-redirect=true";
	}

	public String redirectToAddTournament() {
		collapseAllMenuItems();
		panelMenuBean.setTournamentExpanded(true);
		return "/secured/layout/tournament/add_tournament.xhtml?faces-redirect=true";
	}

	public String redirectToAddCompetition() {
		collapseAllMenuItems();
		panelMenuBean.setCompetitionExpanded(true);
		return "/secured/layout/competition/add_competition.xhtml?faces-redirect=true";
	}

	public String redirectToAddCompetitor() {
		collapseAllMenuItems();
		panelMenuBean.setCompetitorExpanded(true);
		return "/secured/layout/competitor/add_competitor.xhtml?faces-redirect=true";
	}

	public String redirectToRegisterCompetitor() {
		collapseAllMenuItems();
		panelMenuBean.setCompetitorExpanded(true);
		return "/secured/layout/competitor/register_competitor.xhtml?faces-redirect=true";
	}

	public String redirectToAddFirearm() {
		collapseAllMenuItems();
		panelMenuBean.setFirearmExpanded(true);
		return "/secured/layout/firearm/add_firearm.xhtml?faces-redirect=true";
	}

	public String redirectToGssfScoresheet() {
		collapseAllMenuItems();
		panelMenuBean.setScoringExpanded(true);
		return "/secured/layout/gssf/indoor_scoresheet.xhtml?faces-redirect=true";
	}

	public String redirectToBullseyeScoresheet() {
		collapseAllMenuItems();
		panelMenuBean.setBullseyeExpanded(true);
		return "/secured/layout/bullseye/bullseye_scoresheet.xhtml?faces-redirect=true";
	}

	public String redirectToViewBullseyeScores() {
		collapseAllMenuItems();
		panelMenuBean.setResultsExpanded(true);
		return "/secured/layout/scores/view_scores_bullseye.xhtml?faces-redirect=true";
	}

	public String redirectToUpdateScores() {
		collapseAllMenuItems();
		panelMenuBean.setScoringExpanded(true);
		return "/secured/layout/scores/update_scores.xhtml?faces-redirect=true";
	}

	public String redirectToViewGssfScores() {
		collapseAllMenuItems();
		panelMenuBean.setResultsExpanded(true);
		return "/secured/layout/scores/view_scores.xhtml?faces-redirect=true";
	}

	public void collapseAllMenuItems() {
		panelMenuBean.setAdminExpanded(false);
		panelMenuBean.setQuickstartExpanded(false);
		panelMenuBean.setTournamentExpanded(false);
		panelMenuBean.setCompetitionExpanded(false);
		panelMenuBean.setCompetitorExpanded(false);
		panelMenuBean.setFirearmExpanded(false);
		panelMenuBean.setScoringExpanded(false);
		panelMenuBean.setBullseyeExpanded(false);
		panelMenuBean.setResultsExpanded(false);
	}

	public PanelMenuBean getPanelMenuBean() {
		return panelMenuBean;
	}

	public void setPanelMenuBean(PanelMenuBean panelMenuBean) {
		this.panelMenuBean = panelMenuBean;
	}
}
